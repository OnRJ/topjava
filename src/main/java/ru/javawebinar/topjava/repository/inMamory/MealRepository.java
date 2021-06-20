package ru.javawebinar.topjava.repository.inMamory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MealRepository implements UserMealRepository {
    private static List<Meal> meals = new ArrayList<>();

    public MealRepository() {
        meals.add(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public List<Meal> getAll(){
        List<Meal> newList = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            newList.add(new Meal(i, meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        }
        meals = newList;
        return meals;
    }

    public Meal get(int id){
        return meals.get(id);
    }

    public Meal update(Meal meal, Integer id){
        meal.setId(id);
        meals.set(id, meal);
        return meal;
    }

    public Meal create(Meal meal) {
        meal.setId(meals.size());
        meals.add(meal);
        return meal;
    }

    public void delete(int id){
        meals.remove(id);
    }
}
