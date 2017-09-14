package com.bogdevich.cafe.service.factory;

import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.service.impl.UserServiceImpl;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.impl.TransactionManager;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl((ITransactionManager) TransactionManager.getInstance());

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }
}
