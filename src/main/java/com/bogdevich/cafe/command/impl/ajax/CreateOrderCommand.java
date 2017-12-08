package com.bogdevich.cafe.command.impl.ajax;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.command.util.LocalizationManager;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.Order;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.OrderService;
import com.bogdevich.cafe.service.UserService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CreateOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private final String BUNDLE_NAME = "localization/message";
    private final String EXCEPTION_MESSAGE_KEY = "order.failed";
    private final String SUCCESS_MESSAGE_KEY = "order.created";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private OrderService orderService = serviceFactory.getOrderService();
    private DishService dishService = serviceFactory.getDishService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String street = request.getParameter(Constant.ParameterName.STREET);
        String house = request.getParameter(Constant.ParameterName.HOUSE);
        String flat = request.getParameter(Constant.ParameterName.FLAT);
        String number = request.getParameter(Constant.ParameterName.NUMBER);
        String language = (String) session.getAttribute(Constant.AttributeName.LANGUAGE);
        String message = LocalizationManager.getMessage(BUNDLE_NAME, language, EXCEPTION_MESSAGE_KEY);
        try {
            int userID = 0;
            Optional<Order> orderOptional = Optional.empty();
            Optional userIdOptional = Optional.ofNullable(session.getAttribute(Constant.AttributeName.USER_ID));
            if (userIdOptional.isPresent()) {
                userID = (Integer) userIdOptional.get();
                orderOptional = orderService.createOrder(number, street, house, flat, userID);
            } else {
                List<User> guestList = userService.findUserByRoleId(Role.GUEST);
                if (!guestList.isEmpty()) {
                    User guestUser = guestList.get(0);
                    userID = guestUser.getId();
                    Map<Dish, Integer> shoppingCart = dishService.getMapAttributeFromSession(session);
                    orderOptional = orderService.createOrder(number, street, house, flat, userID, shoppingCart);
                }
            }
            if (orderOptional.isPresent()) {
                message = LocalizationManager.getMessage(BUNDLE_NAME, language, SUCCESS_MESSAGE_KEY);
            }
            String answer = new Gson().toJson(message);
            response.setContentType(Constant.JSON_CONTENT_TYPE);
            response.getWriter().println(answer);
        }  catch (InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            String errMessage = LocalizationManager.getMessage(BUNDLE_NAME, language, EXCEPTION_MESSAGE_KEY);
            String answer = new Gson().toJson(errMessage);
            response.setContentType(Constant.JSON_CONTENT_TYPE);
            response.getWriter().println(answer);
        } catch (ServiceException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            String errMessage = LocalizationManager.getMessage(BUNDLE_NAME, language, EXCEPTION_MESSAGE_KEY);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errMessage);
        }
    }
}
