package com.bogdevich.cafe.controller.listener;

import com.bogdevich.cafe.command.constant.Constant;
import com.bogdevich.cafe.entity.type.Role;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener{

    public static final Logger LOGGER = LogManager.getLogger();

    public HttpSessionListenerImpl() {
    }

    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(Constant.AttributeName.ROLE, Role.GUEST);
        session.setAttribute(Constant.AttributeName.LANGUAGE, Constant.ru_RU);
        LOGGER.log(Level.DEBUG, "Session created::ID="+session.getId());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        LOGGER.log(Level.DEBUG, "Session destroyed::ID="+se.getSession().getId());
    }
}
