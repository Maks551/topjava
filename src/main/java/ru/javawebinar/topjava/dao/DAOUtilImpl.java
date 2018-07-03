package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DAOUtilImpl implements DAOUtil {
    private MealsUtil mealsUtil;

    public DAOUtilImpl(){
        mealsUtil = new MealsUtil();
    }

    @Override
    public ConcurrentHashMap<Integer, Meal> getMealConcurrentHashMap() {
        return mealsUtil.getMealConcurrentHashMap();
    }

    @Override
    public void addMeal(Meal meal) {
        getMealConcurrentHashMap().putIfAbsent(meal.getId(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        getMealConcurrentHashMap().replace(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        getMealConcurrentHashMap().remove(mealId);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(getMealConcurrentHashMap().values());
    }

    @Override
    public Meal getMealById(int mealId) {
        return getMealConcurrentHashMap().get(mealId);
    }
}
