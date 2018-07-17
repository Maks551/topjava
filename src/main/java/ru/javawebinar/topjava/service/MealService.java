package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealService {
    Meal get(int id, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Meal update(Meal meal, int userId) throws NotFoundException;

    Meal create(Meal meal, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getAllBetweenDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    default List<Meal> getAllBetweenDate(LocalDate startDate, LocalDate endDate, int userId){
        return getAllBetweenDateTime(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }
}