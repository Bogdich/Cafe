package com.bogdevich.cafe.command.impl.redirect;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.constant.Constant;
import com.bogdevich.cafe.constant.JavaServerPagePath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class RegistrationPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional
                .ofNullable(request.getSession().getAttribute(Constant.AttributeName.ERROR_MESSAGE))
                .ifPresent(attribute -> {
                    request.setAttribute(Constant.AttributeName.ERROR_MESSAGE, attribute);
                    request.getSession().removeAttribute(Constant.AttributeName.ERROR_MESSAGE);
                });
        request.getRequestDispatcher(JavaServerPagePath.REGISTRATION_PAGE).forward(request, response);
    }
}
