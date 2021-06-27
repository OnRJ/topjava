package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    public InMemoryMealRepository() {
        MealsUtil.meals.forEach(this::save);
   }

    public Collection<Meal> getAll(){
        log.info("getAll");
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == authUserId())
                .collect(Collectors.toList());
    }

    public Meal get(int id){
        log.info("get {}", id);
        return repository.get(id);
    }

    public Meal save(Meal meal){
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    public boolean delete(int id){
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }
}
