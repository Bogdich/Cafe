package com.bogdevich.cafe.command.impl.ajax;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.command.util.LocalizationManager;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.wrapper.Message;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.factory.ServiceFactory;
import com.bogdevich.cafe.service.util.Validator;
import com.google.gson.Gson;
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

public class AddDishToShoppingCartCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();

    private final String DEFAULT_QUANTITY = "1";
    private final String BUNDLE_NAME = "localization/message";
    private final String EXCEPTION_MESSAGE_KEY = "message.addDishException";
    private final String SUCCESS_MESSAGE_KEY = "message.addDishSuccess";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DishService dishService = serviceFactory.getDishService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Message message;
        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(Constant.AttributeName.LANGUAGE);
        String quantity = Optional
                .ofNullable(request.getParameter(Constant.ParameterName.DISH_QUANTITY))
                .orElse(DEFAULT_QUANTITY);
        String dishID = request.getParameter(Constant.ParameterName.DISH_ID);
        Optional<Integer> optionalUserID= Optional
                .ofNullable((Integer) session.getAttribute(Constant.AttributeName.USER_ID));
        if (optionalUserID.isPresent()) {
            Integer userID = optionalUserID.get();
            message = authorizedExecution(userID, dishID, quantity, language);
        } else {
            message = nonAuthorizedExecution(session, dishID, quantity, language);
        }
        String answer = new Gson().toJson(message);
        response.setContentType(Constant.JSON_CONTENT_TYPE);
        response.getWriter().println(answer);
    }

    private Message authorizedExecution(Integer userID, String dishID, String quantity, String languageTag){
        try {
            dishService.createUserDishRecord(userID, dishID, quantity);
            String message = LocalizationManager.getMessage(BUNDLE_NAME, languageTag, SUCCESS_MESSAGE_KEY);
            return new Message(message);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            String message = LocalizationManager.getMessage(BUNDLE_NAME, languageTag, EXCEPTION_MESSAGE_KEY);
            return new Message(message);
        }
    }

    private Message nonAuthorizedExecution(HttpSession session, String dishID, String quantity, String languageTag){
        String message;
        try {
            Optional<Dish> dishOptional = dishService.findDishById(dishID);
            if (dishOptional.isPresent()) {
                int validQuantity = Validator.parseDishQuantity(quantity);
                Dish dish = dishOptional.get();
                Map<Dish, Integer> shoppingCart = getMapAttributeFromSession(session);
                if (shoppingCart.containsKey(dish)) {
                    int totalQuantity = shoppingCart.get(dish) + validQuantity;
                    shoppingCart.put(dish, totalQuantity);
                } else {
                    shoppingCart.put(dish, validQuantity);
                }

                message = LocalizationManager.getMessage(BUNDLE_NAME, languageTag, SUCCESS_MESSAGE_KEY);
            } else {
                message = LocalizationManager.getMessage(BUNDLE_NAME, languageTag, EXCEPTION_MESSAGE_KEY);
            }
            return new Message(message);
        } catch (ServiceException | InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            message = LocalizationManager.getMessage(BUNDLE_NAME, languageTag, EXCEPTION_MESSAGE_KEY);
            return new Message(message);
        }
    }

    @SuppressWarnings("unchecked")
    private HashMap<Dish, Integer> getMapAttributeFromSession(HttpSession session) {
        Object mapObject = Optional
                .ofNullable(session.getAttribute(Constant.AttributeName.SHOPPING_CART))
                .orElseGet(() -> {
                    Map<Dish, Integer> dishIntegerMap = new HashMap<>();
                    session.setAttribute(Constant.AttributeName.SHOPPING_CART, dishIntegerMap);
                    return dishIntegerMap;
                });
        return (HashMap<Dish, Integer>) mapObject;
    }
}
