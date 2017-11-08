package com.bogdevich.cafe.service.factory;

import com.bogdevich.cafe.service.DishCategoryService;
import com.bogdevich.cafe.service.DishService;
import com.bogdevich.cafe.service.OrderService;
import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.service.impl.DishCategoryServiceImpl;
import com.bogdevich.cafe.service.impl.DishServiceImpl;
import com.bogdevich.cafe.service.impl.OrderServiceImpl;
import com.bogdevich.cafe.service.impl.UserServiceImpl;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.impl.TransactionManager;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    private final UserService userService = new UserServiceImpl((ITransactionManager) TransactionManager.getInstance());

    private final DishService dishService = new DishServiceImpl((ITransactionManager) TransactionManager.getInstance());

    private final OrderService orderService = new OrderServiceImpl((ITransactionManager) TransactionManager.getInstance());

    private final DishCategoryService dishCategoryService = new DishCategoryServiceImpl((ITransactionManager) TransactionManager.getInstance());

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public DishService getDishService() {
        return dishService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public DishCategoryService getDishCategoryService() {
        return dishCategoryService;
    }
}
