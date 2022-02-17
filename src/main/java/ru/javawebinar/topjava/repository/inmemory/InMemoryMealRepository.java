package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    static List<Meal> list = new ArrayList<>(Arrays.asList(
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(authUserId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)));


    {
        MealsUtil.meals.forEach(this::save);
    }

    public static void main(String[] args) {
        InMemoryMealRepository inMemoryMealRepository = new InMemoryMealRepository();
        inMemoryMealRepository
                .save(new Meal(null,1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 20000));
        System.out.println(list);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew() && meal.getUserId() == authUserId()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        } else {
            return list.stream().filter(m -> meal.getUserId() == authUserId()).findFirst().orElse(null);
        }
        // handle case: update, but not present in storage
//            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal)
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        log.info("getAll");
        return repository.values();
    }

    @Override
    public String toString() {
        return "InMemoryMealRepository{" +
                "repository=" + repository +
                ", counter=" + counter +
                '}';
    }
}

