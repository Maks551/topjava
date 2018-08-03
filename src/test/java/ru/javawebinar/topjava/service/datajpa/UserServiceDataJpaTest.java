package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(profiles = "datajpa")
public class UserServiceDataJpaTest extends UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void getUserWithMeal() throws Exception{
        User user = service.getWithMeal(USER_ID);
        UserTestData.assertMatch(user, USER);
        assertMatch(user.getMeals(), MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void getUserWithMealNotFoundUser() throws Exception{
        service.getWithMeal(1);
    }

    @Test(expected = AssertionError.class)
    public void getUserWithMealAssertionErrorMeals() throws Exception{
        User user = service.getWithMeal(USER_ID);
        UserTestData.assertMatch(user, USER);
        assertMatch(user.getMeals(), ADMIN_MEAL2, ADMIN_MEAL1);
    }
}
