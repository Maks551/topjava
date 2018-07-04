package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAOUtil;
import ru.javawebinar.topjava.dao.DAOUtilImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DAOUtil dao = new DAOUtilImpl();
    private static final String LIST_MEALS = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final DateTimeFormatter FORMATTER =DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("MealServlet - doGet()");
        String action = req.getParameter("action");

        if (action != null) {
            String forward;
            switch (action) {
                case "delete": {
                    int mealId = Integer.parseInt(req.getParameter("mealId"));
                    log.debug("delete meal from id = " + mealId);
                    dao.deleteMeal(mealId);
                    log.debug("redirect to " + LIST_MEALS);
                    resp.sendRedirect("meals");
                    return;
                }
                case "edit": {
                    int mealId = Integer.parseInt(req.getParameter("mealId"));
                    log.debug("edit meal from id = " + mealId);
                    forward = INSERT_OR_EDIT;
                    Meal meal = dao.getMealById(mealId);
                    req.setAttribute("meal", meal);
                    log.debug("forward to " + INSERT_OR_EDIT);
                    break;
                }
                case "listMeal":
                    forward = LIST_MEALS;
                    req.setAttribute("mealList", getMealWithExceeds());
                    log.debug("forward to " + LIST_MEALS);
                    break;
                default:
                    forward = INSERT_OR_EDIT;
                    log.debug("forward to " + INSERT_OR_EDIT);
                    break;
            }
            req.getRequestDispatcher(forward).forward(req, resp);
        }
        req.setAttribute("mealList", getMealWithExceeds());
        req.getRequestDispatcher(LIST_MEALS).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        log.debug("MealServlet - doPost()");

        String date = req.getParameter("date");
        LocalDateTime dateTime = LocalDateTime.parse(date, FORMATTER);

        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        String mealId = req.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()){
            log.debug("Add meal " + meal);
            dao.addMeal(meal);
        } else {
            log.debug("Edit meal from id = " + mealId);
            meal.setId(Integer.parseInt(mealId));
            dao.updateMeal(meal);
        }
        log.debug("forward to " + LIST_MEALS);
        RequestDispatcher view = req.getRequestDispatcher(LIST_MEALS);
        req.setAttribute("mealList", getMealWithExceeds());
        view.forward(req, resp);
    }

    private List<MealWithExceed> getMealWithExceeds() {
        return dao.getAllMeals().stream()
                .map(s -> new MealWithExceed(s, s.getCalories() > 500))
                .collect(Collectors.toList());
    }
}
