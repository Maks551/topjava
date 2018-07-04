package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface DAOUtil {
    ConcurrentHashMap<Integer, Meal> getMealConcurrentHashMap();
    void addMeal(Meal meal);
    void updateMeal(Meal meal);
    void deleteMeal(Integer mealId);
    List<Meal> getAllMeals();
    Meal getMealById(Integer mealId);
}
