package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ;

    public static final Meal MEAL_1 = new Meal(MEAL_ID, USER_ID, LocalDateTime.of(2018 , 5, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_2 = new Meal(MEAL_ID + 1, USER_ID, LocalDateTime.of(2018 , 5, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_3 = new Meal(MEAL_ID + 2, USER_ID, LocalDateTime.of(2018 , 5, 30, 21, 0), "Ужин", 500);
    public static final Meal MEAL_4 = new Meal(MEAL_ID + 3, USER_ID, LocalDateTime.of(2018 , 5, 31, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_5 = new Meal(MEAL_ID + 4, USER_ID, LocalDateTime.of(2018 , 5, 31, 13, 0), "Обед", 1000);
    public static final Meal MEAL_6 = new Meal(MEAL_ID + 5, USER_ID, LocalDateTime.of(2018 , 5, 31, 21, 0), "Ужин", 510);
    public static final Meal MEAL_7 = new Meal(MEAL_ID + 6, ADMIN_ID, LocalDateTime.of(2018 , 6, 1, 14, 0), "Админ ланч", 510);
    public static final Meal MEAL_8 = new Meal(MEAL_ID + 7, ADMIN_ID, LocalDateTime.of(2018 , 6, 1, 21, 0), "Админ ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }
    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
//        assertThat(actual).usingElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
        assertThat(actual).isEqualTo(expected);
    }
}
