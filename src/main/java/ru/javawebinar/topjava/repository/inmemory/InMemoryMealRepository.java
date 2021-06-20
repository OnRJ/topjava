package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private static List<Meal> meals = new ArrayList<>();

    public InMemoryMealRepository() {
        meals.add(new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 0));
        meals.add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 0));
        meals.add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 0));
        meals.add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 0));
        meals.add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 0));
        meals.add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 0));
        meals.add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 0));
    }

    public List<Meal> getAll(int userId){
        log.info("getAll");
        List<Meal> newList = new ArrayList<>();
        for (int i = 0; i < meals.size(); i++) {
            Meal meal = meals.get(i);
            newList.add(new Meal(i, meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getUserId()));
        }
        meals = newList;
        return meals;
    }

    public Meal get(int id, int userId){
        log.info("get {}", id);
        return meals.get(id);
    }

    public Meal save(Meal meal, int userId){
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(meals.size());
            meals.add(meal);
        } else {
            meals.set(meal.getId(), meal);
        }
        return meal;
    }

    public boolean delete(int id, int userId){
        log.info("delete {}", id);
        return meals.remove(id) == null;
    }
}
