package com.bogdevich.cafe.controller.listener;

import com.bogdevich.cafe.connectionpool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    public ServletContextListenerImpl() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance().init();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
    }
}
