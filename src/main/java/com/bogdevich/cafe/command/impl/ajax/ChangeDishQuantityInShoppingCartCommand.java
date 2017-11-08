package com.bogdevich.cafe.command.impl.ajax;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.factory.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class ChangeDishQuantityInShoppingCartCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger();

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private DishService dishService = serviceFactory.getDishService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dishID = request.getParameter(Constant.ParameterName.DISH_ID);
        String quantity = request.getParameter(Constant.ParameterName.DISH_QUANTITY);
        HttpSession session = request.getSession();
        Optional userIdOptional = Optional
                .ofNullable(session.getAttribute(Constant.AttributeName.USER_ID));
        try {
            if (userIdOptional.isPresent()) {
                int userID = (Integer) userIdOptional.get();
                dishService.changeDishQuantityInShoppingCart(userID, dishID, quantity);
            } else {
                dishService.changeDishQuantityInShoppingCart(session, dishID, quantity);
            }
        } catch (InvalidDataException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ServiceException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
