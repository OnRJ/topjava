package ru.javawebinar.topjava.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id, int userId) {
        return service.get(id, userId);
    }

    public void delete(int id, int userId) {
        service.delete(id, userId);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.filtered(service.getAll(userId));
    }

    public Meal create(Meal meal, int userId) {
        return service.create(meal, userId);
    }

    public void save(Meal meal, int userId) {
        service.save(meal, userId);
    }
}
