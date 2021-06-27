package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealRestController controller;
    private static final String ADD_OR_EDIT = "/edit.jsp";
    private static final String LIST_MEAL = "/meals.jsp";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controller = new MealRestController(new MealService(new InMemoryMealRepository()));
    }

    @Override
    public void destroy() {
        //springContext.close();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if (action == null) {
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher(LIST_MEAL).forward(request, response);
            log.debug("get all meals");
        } else if (action.equalsIgnoreCase("edit")) {
            if (id != null) {
                Meal meal = controller.get(Integer.parseInt(id));
                request.setAttribute("meal", meal);
            }
            request.getRequestDispatcher(ADD_OR_EDIT).forward(request, response);
            log.debug("redirect to edit/add meal");
        } else if (action.equalsIgnoreCase("delete")){
            controller.delete(Integer.parseInt(id));
            response.sendRedirect("meals");
            log.debug("delete meal with id - " + Integer.parseInt(id));
        } else {
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher(LIST_MEAL).forward(request, response);
            log.debug("get all meals");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), formatter);

        Meal meal = new Meal(dateTime,
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                SecurityUtil.authUserId());

        if (StringUtils.hasLength(request.getParameter("id"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            controller.save(meal, id);
            log.debug("updated meal with id - " + id);
        } else {
            controller.save(meal, meal.getUserId());
            log.debug("created new meal");
        }

        RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
        request.setAttribute("mealList", controller.getAll());
        view.forward(request, response);
    }
}
