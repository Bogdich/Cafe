package com.bogdevich.cafe.command.impl.common;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LanguageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request
                .getSession()
                .setAttribute(
                        Constant.AttributeName.LANGUAGE,
                        Optional
                                .ofNullable(request.getParameter(Constant.ParameterName.LANGUAGE))
                                .orElse(Constant.ru_RU)
                )
        ;
        String location = String.join("", request.getContextPath(), Constant.CONTROLLER, Constant.ActionName.MAIN_PAGE);
        response.sendRedirect(location);
    }
}
