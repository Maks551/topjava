package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(profiles = "datajpa")
public class MealServiceDataJpaTest extends MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void getWithUser(){
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        User user = meal.getUser();
        assertMatch(meal, MEAL1);
        UserTestData.assertMatch(user, USER);
    }

    @Test(expected = NotFoundException.class)
    public void getWithUserNotFoundMeal(){
        service.getWithUser(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWithUserNotFoundUser(){
        service.getWithUser(MEAL1_ID, 1);
    }
}
