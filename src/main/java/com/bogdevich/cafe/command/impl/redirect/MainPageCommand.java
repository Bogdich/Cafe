package com.bogdevich.cafe.command.impl.redirect;

import com.bogdevich.cafe.command.Command;
import com.bogdevich.cafe.constant.JavaServerPagePath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(JavaServerPagePath.MAIN_PAGE).forward(request, response);
    }
}
