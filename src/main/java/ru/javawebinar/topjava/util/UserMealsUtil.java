package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);

        // test
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000)
                .forEach(s -> System.out.println(s.getDateTime() + " " + s.getDescription() + " " + s.getCalories() + " " + s.isExceed()));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Set<UserMealWithExceed> set = new TreeSet<>(Comparator.comparing(UserMealWithExceed::getDateTime));
        Map<LocalDate, Integer> map = new HashMap<>();
        mealList.stream().peek(s -> map.merge(s.getDateTime().toLocalDate(), s.getCalories(), (a, b) -> a + b))
                .filter(s -> s.getDateTime().toLocalTime().isAfter(startTime) && s.getDateTime().toLocalTime().isBefore(endTime))
                .forEach(s -> {
                    if (map.get(s.getDateTime().toLocalDate()) > caloriesPerDay) {
                        set.add(new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(), true));
                    } else {
                        set.add(new UserMealWithExceed(s.getDateTime(), s.getDescription(), s.getCalories(), false));
                    }
                });
        return new ArrayList<>(set);
    }
}
