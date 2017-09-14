package com.bogdevich.cafe.controller.listener;

import com.bogdevich.cafe.connectionpool.ConnectionPool;
import com.bogdevich.cafe.constant.Constant;
import com.bogdevich.cafe.entity.type.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
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
