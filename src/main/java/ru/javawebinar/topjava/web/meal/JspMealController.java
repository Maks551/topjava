package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends MealRestController {

    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("/meals")
    public String meals(HttpServletRequest request, Model model){
        if (!"filter".equals(request.getParameter("action"))) {
            model.addAttribute("meals", getAll());
        }
        return "meals";
    }

    @GetMapping("/meals/delete/{id}")
    public String deleteMeal(@PathVariable("id") Integer id){
        delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/meals/update/{id}")
    public String update(@PathVariable("id") Integer id, Model model){
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/create")
    public String create(Model model){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/meals/save")
    public String save(HttpServletRequest request){
        String action = request.getParameter("action");
        if (action == null) {
            Meal meal = new Meal(
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            if (request.getParameter("id").isEmpty()) {
                create(meal);
            } else {
                String paramId = Objects.requireNonNull(request.getParameter("id"));
                update(meal, Integer.parseInt(paramId));
            }
        }
        return "redirect:/meals";
    }

    @PostMapping("meals")
    public String filter(HttpServletRequest request, Model model){
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}