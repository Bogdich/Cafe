package com.bogdevich.cafe.command.impl.auth;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.constant.Constant;
import com.bogdevich.cafe.constant.ErrorMessage;
import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.exception.InvalidDataException;
import com.bogdevich.cafe.exception.ReceiverException;
import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.service.factory.ServiceFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String login = request.getParameter(Constant.ParameterName.LOGIN);
        String password = request.getParameter(Constant.ParameterName.PASSWORD);
        String number = request.getParameter(Constant.ParameterName.NUMBER);
        String street = request.getParameter(Constant.ParameterName.STREET);
        String house = request.getParameter(Constant.ParameterName.HOUSE);
        String flat = request.getParameter(Constant.ParameterName.FLAT);

        try {
            userService
                    .register(login, password, number, street, house, flat)
                    .ifPresent(userContainer -> {
                        session.setAttribute(Constant.AttributeName.USER_ID, userContainer.getUser().getId());
                        session.setAttribute(Constant.AttributeName.ROLE, userContainer.getUser().getRole());
                    });
        } catch (ReceiverException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (InvalidDataException ex) {
            session.setAttribute(Constant.AttributeName.ERROR_MESSAGE, ex.getMessage());
            String location = String.join("", request.getContextPath(), Constant.CONTROLLER, Constant.ActionName.REGISTRATION_PAGE);
            response.sendRedirect(location);
        }

        if (null != session.getAttribute(Constant.AttributeName.USER_ID)) {
            String location = String.join("", request.getContextPath(), Constant.CONTROLLER, Constant.ActionName.MAIN_PAGE);
            response.sendRedirect(location);
        } else {
            session.setAttribute(Constant.AttributeName.ERROR_MESSAGE, ErrorMessage.INVALID_INPUT);
            String location = String.join("", request.getContextPath(), Constant.CONTROLLER, Constant.ActionName.REGISTRATION_PAGE);
            response.sendRedirect(location);
        }

    }
}
