package com.bogdevich.cafe.controller.filter;

import com.bogdevich.cafe.command.ActionType;
import com.bogdevich.cafe.constant.Constant;
import com.bogdevich.cafe.entity.type.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class RoleFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String actionName = Optional
                .ofNullable(request.getParameter(Constant.ParameterName.COMMAND))
                .orElse(Constant.ActionName.MAIN_PAGE);

        Role role = (session == null) ? Role.GUEST : (Role) session.getAttribute(Constant.AttributeName.ROLE);
        ActionType type = ActionType.defineActionType(actionName);

        if (type != null){
            if (type.permitAction(role)) {
                chain.doFilter(req, resp);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
