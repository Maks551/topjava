package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query("SElECT m FROM Meal m WHERE m.user.id=:user_id ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("user_id") int user_id);

    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:user_id ORDER BY m.dateTime DESC")
    Meal get(@Param("id")int id, @Param("user_id") int user_id);

    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    int delete(@Param("id")int id, @Param("user_id") int user_id);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:user_id AND m.dateTime BETWEEN :start_date AND :end_date ORDER BY m.dateTime DESC")
    List<Meal> getBetween(@Param("start_date") LocalDateTime start_date, @Param("end_date") LocalDateTime end_date, @Param("user_id") int user_id);
}
