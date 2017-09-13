package com.bogdevich.cafe.command.impl.auth;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.constant.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        request.getSession().invalidate();
        String location = String.join("", request.getContextPath(), Constant.CONTROLLER, Constant.ActionName.MAIN_PAGE);
        response.sendRedirect(location);
    }
}
