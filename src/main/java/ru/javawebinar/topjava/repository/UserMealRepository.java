package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface UserMealRepository {
    Collection<Meal> getAll();
    Meal get(int id);
    Meal update(Meal meal, Integer id);
    Meal create(Meal meal);
    void delete(int id);
}
