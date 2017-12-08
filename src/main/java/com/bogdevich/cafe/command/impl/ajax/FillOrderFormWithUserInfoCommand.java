package com.bogdevich.cafe.command.impl.ajax;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.entity.bean.UserInfo;
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
import java.util.Optional;

public class FillOrderFormWithUserInfoCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Optional<Object> idOptional = Optional.ofNullable(session.getAttribute(Constant.AttributeName.USER_ID));
        if (idOptional.isPresent()) {
            int userID = (Integer) idOptional.get();
            try {
                Optional<UserInfo> userInfoOptional = userService.findUserInfo(userID);
                if (userInfoOptional.isPresent()) {
                    UserInfo userInfo = userInfoOptional.get();
                    String answer = new Gson().toJson(userInfo);
                    response.setContentType(Constant.JSON_CONTENT_TYPE);
                    response.getWriter().println(answer);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (InvalidDataException ex) {
                LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (ServiceException ex) {
                LOGGER.log(Level.ERROR, ex.getMessage(), ex.getCause());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
