package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertMatch(meal, MEAL_1);
    }

    @Test (expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID + 6, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), MEAL_8);
    }

    @Test (expected = NotFoundException.class)
    public void deleteNotFoundAdmin() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test (expected = NotFoundException.class)
    public void deleteNotFoundUser() {
        service.delete(MEAL_ID + 7, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> betweenDates = service.getBetweenDates(LocalDate.of(2018, 5, 29),
                LocalDate.of(2018, 5, 30), USER_ID);
        assertMatch(betweenDates, MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(
                LocalDateTime.of(2018, 5, 29, 12, 0),
                LocalDateTime.of(2018, 5, 31, 15, 0), USER_ID);
        assertMatch(betweenDateTimes, MEAL_5, MEAL_2);
    }

    @Test
    public void getAll() {
        List<Meal> allMeals = service.getAll(USER_ID);
        assertMatch(allMeals, MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_1);
        updated.setCalories(50);
        updated.setDescription("new description");
        updated.setDateTime(LocalDateTime.of(2018, 7, 20, 16, 0));
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test (expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(MEAL_1, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, USER_ID, LocalDateTime.of(2018, 7, 20, 10, 0), "new description", 400);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6, newMeal);
    }
}