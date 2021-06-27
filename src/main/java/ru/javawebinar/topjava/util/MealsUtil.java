package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int CALORIES_PER_DAY = 2000;
    public static final List<Meal> meals = new ArrayList<>();

    static {
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 0));
        meals.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин не твой", 410, 1));

    }

    public static List<MealTo> filtered(Collection<Meal> meals) {
        Map<LocalDate, Integer> map = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        return meals.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
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
