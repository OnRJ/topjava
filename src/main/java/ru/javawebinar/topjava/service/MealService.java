package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inMamory.MealRepository;

import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(@Qualifier("jdbcMealRepository") MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id) {
        return repository.get(id);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public void update(Meal meal, Integer id) {
        Assert.notNull(meal, "meal must not be null");
        repository.update(meal, id);
    }

    public Meal create(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        return repository.create(meal);
    }
}
