package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int CALORIES_PER_DAY = 2000;

    private MealsUtil(){

    }

    public static List<MealTo> filtered(Collection<Meal> meals) {
        Map<LocalDate, Integer> map = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .map(meal -> new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), map.get(meal.getDateTime().toLocalDate()) > CALORIES_PER_DAY))
                .collect(Collectors.toList());
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
        Map<LocalDate, Integer> userMeal = new HashMap<>();
        List<MealTo> userMealWithExcess = new ArrayList<>();

        for (Meal meal:meals) {
            userMeal.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        for (Meal meal: meals) {
            if(DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcess.add(new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        userMeal.get(meal.getDateTime().toLocalDate()) > CALORIES_PER_DAY));
            }
        }
        return userMealWithExcess;
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime) {
        Map<LocalDate, Integer> userMeal = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        userMeal.get(meal.getDateTime().toLocalDate()) > CALORIES_PER_DAY))
                .collect(Collectors.toList());
    }
}
