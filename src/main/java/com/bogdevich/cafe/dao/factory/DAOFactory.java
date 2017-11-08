package com.bogdevich.cafe.dao.factory;

import com.bogdevich.cafe.dao.DishDAO;
import com.bogdevich.cafe.dao.OrderDAO;
import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.UserInfoDAO;
import com.bogdevich.cafe.dao.impl.DishDAOImpl;
import com.bogdevich.cafe.dao.impl.OrderDAOImpl;
import com.bogdevich.cafe.dao.impl.UserDAOImpl;
import com.bogdevich.cafe.dao.impl.UserInfoDAOImpl;
import com.bogdevich.cafe.transaction.IDataSource;
import com.bogdevich.cafe.transaction.impl.TransactionManager;

public class DAOFactory {
    private static DAOFactory instance = new DAOFactory();

    private final UserDAO userDAO = new UserDAOImpl((IDataSource) TransactionManager.getInstance());
    private final UserInfoDAO userInfoDAO = new UserInfoDAOImpl((IDataSource) TransactionManager.getInstance());
    private final DishDAO dishDAO = new DishDAOImpl((IDataSource) TransactionManager.getInstance());
    private final OrderDAO orderDAO = new OrderDAOImpl((IDataSource) TransactionManager.getInstance());

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public UserInfoDAO getUserInfoDAO() {
        return userInfoDAO;
    }

    public DishDAO getDishDAO() {
        return dishDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }
}
