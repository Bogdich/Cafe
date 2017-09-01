package com.bogdevich.cafe.dao.factory;

import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.impl.UserDAOImpl;
import com.bogdevich.cafe.transaction.IDataSource;
import com.bogdevich.cafe.transaction.impl.TransactionManager;

public class DAOFactory {
    private static DAOFactory instance = new DAOFactory();

    private UserDAO userDAO = new UserDAOImpl((IDataSource) TransactionManager.getInstance());

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
