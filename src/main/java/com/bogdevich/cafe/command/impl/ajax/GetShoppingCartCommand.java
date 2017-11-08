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
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GetShoppingCartCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DishService dishService = serviceFactory.getDishService();
    private DishCategoryService dishCategoryService = serviceFactory.getDishCategoryService();



    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String answer;
        HttpSession session = request.getSession();
        Optional userIdOptional = Optional
                .ofNullable(session.getAttribute(Constant.AttributeName.USER_ID));
        try {
            if (userIdOptional.isPresent()) {
                int userID = (Integer) userIdOptional.get();
                answer = authorizedExecution(userID);
            } else {
                answer = nonAuthorizedExecution(session);
            }
            response.setContentType(Constant.JSON_CONTENT_TYPE);
            response.getWriter().println(answer);
        } catch (InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    private String authorizedExecution(int userID) throws ServiceException, InvalidDataException{
        Map<Dish, Integer> shoppingCart =  dishService.findUserDishMap(userID);
        Gson gson = new GsonBuilder().disableHtmlEscaping().enableComplexMapKeySerialization().create();
        return gson.toJson(shoppingCart);
    }

    private String nonAuthorizedExecution(HttpSession session) {
        Map<Dish, Integer> shoppingCart = getMapAttributeFromSession(session);
        Gson gson = new GsonBuilder().disableHtmlEscaping().enableComplexMapKeySerialization().create();
        return gson.toJson(shoppingCart);
    }

    @SuppressWarnings("unchecked")
    private HashMap<Dish, Integer> getMapAttributeFromSession(HttpSession session) {
        Object mapObject = Optional
                .ofNullable(session.getAttribute(Constant.AttributeName.SHOPPING_CART))
                .orElseGet(HashMap::new);
        return (HashMap<Dish, Integer>) mapObject;
    }
}
