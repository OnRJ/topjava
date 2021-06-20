package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.util.List;

@Service
public class MealService {

    private final InMemoryMealRepository repository;

    public MealService(@Qualifier("jdbcMealRepository") InMemoryMealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        return repository.get(id, userId);
    }

    public void delete(int id, int userId) {
        repository.delete(id, userId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void save(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        repository.save(meal, userId);
    }

    public Meal create(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal, userId);
    }
}
