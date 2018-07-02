package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.DAOUtil;
import ru.javawebinar.topjava.dao.DAOUtilImpl;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DAOUtil util = new DAOUtilImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("forward to meals");

        List<MealWithExceed> mealWithExceedList = util.getMealConcurrentHashMap().values().stream()
                .map(s -> new MealWithExceed(s, s.getCalories() > 500))
                .collect(Collectors.toList());

        req.setAttribute("mealList", mealWithExceedList);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
