package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.CALORIES_PER_DAY;

public class SecurityUtil {

    public static int authUserId() {
        return 1;
    }

    public static int authUserCaloriesPerDay() {
        return CALORIES_PER_DAY;
    }
}