package com.bogdevich.cafe.command.impl.ajax;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.service.DishCategoryService;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.factory.ServiceFactory;
import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetDishesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DishService dishService = serviceFactory.getDishService();
    private DishCategoryService dishCategoryService = serviceFactory.getDishCategoryService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter(Constant.ParameterName.CATEGORY);
        try {
            List<Dish> dishList = dishService.findDishByCategoryName(categoryName);
            String answer = new Gson().toJson(dishList);
            response.setContentType(Constant.JSON_CONTENT_TYPE);
            response.getWriter().println(answer);
        } catch (ServiceException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
