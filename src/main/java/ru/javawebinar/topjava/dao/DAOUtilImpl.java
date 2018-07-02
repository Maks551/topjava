package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class DAOUtilImpl implements DAOUtil {
    private static ConcurrentHashMap<Integer, Meal> mealConcurrentHashMap = new ConcurrentHashMap<>();

    static {
        mealConcurrentHashMap.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealConcurrentHashMap.put(2, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealConcurrentHashMap.put(3, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealConcurrentHashMap.put(4, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealConcurrentHashMap.put(5, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealConcurrentHashMap.put(6, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public ConcurrentHashMap<Integer, Meal> getMealConcurrentHashMap() {
        return mealConcurrentHashMap;
    }

    @Override
    public void addMeal(Meal meal) {
        mealConcurrentHashMap.putIfAbsent(meal.getId(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealConcurrentHashMap.replace(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        mealConcurrentHashMap.remove(mealId);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(mealConcurrentHashMap.values());
    }

    @Override
    public Meal getMealById(int mealId) {
        return mealConcurrentHashMap.get(mealId);
    }
}
