package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class MealWithExceed {
    private static AtomicInteger id = new AtomicInteger();
    private final int mealWithExceedId;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(Meal meal, boolean exceed){
        mealWithExceedId = meal.getId();
        dateTime = meal.getDateTime();
        description = meal.getDescription();
        calories = meal.getCalories();
        this.exceed = exceed;
    }

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        mealWithExceedId = id.incrementAndGet();
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public int getId(){
        return mealWithExceedId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealWithExceed{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}