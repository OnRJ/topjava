package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Service
public class MealService {

    private final InMemoryMealRepository repository;

    public MealService(InMemoryMealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id) {
        checkNotFound(id != authUserId() || repository.get(id) != null, "meal id " + id);
        return repository.get(id);
    }

    public void delete(int id) {
        checkNotFound(id != authUserId() || repository.get(id) != null, "meal id " + id);
        repository.delete(id);
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public void save(Meal meal, int id) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(meal, id);
        checkNotFound(meal.getUserId() == authUserId(), "meal id " + meal.getId());
        repository.save(meal);
    }
}
