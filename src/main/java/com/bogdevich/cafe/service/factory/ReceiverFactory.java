package com.bogdevich.cafe.service.factory;

import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.service.impl.UserServiceImpl;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.impl.TransactionManager;

public class ReceiverFactory {
    private static ReceiverFactory instance = new ReceiverFactory();

    private final UserService userService = new UserServiceImpl((ITransactionManager) TransactionManager.getInstance());

    private ReceiverFactory() {
    }

    public static ReceiverFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }
}
