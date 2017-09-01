package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.factory.DAOFactory;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.enums.Role;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.exception.ReceiverException;
import com.bogdevich.cafe.exception.TransactionException;
import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.transaction.ITransactionManager;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.bogdevich.cafe.service.util.PasswordUtil.*;

public class UserServiceImpl implements UserService {

    private ITransactionManager transactionManager;
    private DAOFactory daoFactory;

    public UserServiceImpl(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    public Optional<User> login(String login, String password) throws ReceiverException {
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            return transactionManager
                    .executeInTransaction(()-> userDAO.findUserByLogin(login))
                    .filter(user1 -> checkPassword(password, user1.getPassword()));
        } catch (TransactionException ex) {
            throw new ReceiverException(ex.getMessage(),ex);
        }
    }

    @Override
    public Optional<User> register(String login, String email, String password, Role role) throws ReceiverException {
        //validator
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            if (transactionManager
                    .executeInTransaction(()-> userDAO.findUserByLogin(login))
                    .isPresent()) {
                throw new ReceiverException("Login is already taken");
            }
            return transactionManager
                    .executeInTransaction(() -> userDAO.create(login, email, password, role));
        } catch (TransactionException ex) {
            throw new ReceiverException(ex);
        }
    }

    @Override
    public List<User> findAllUsers() throws ReceiverException {
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            return transactionManager
                    .executeInTransaction(userDAO::findAll)
                    .stream()
                    .sorted(Comparator.comparing(User::getLogin))
                    .collect(Collectors.toList());
        } catch (TransactionException ex) {
            throw new ReceiverException(ex);
        }
    }
}
