package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.constant.ErrorMessage;
import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.UserInfoDAO;
import com.bogdevich.cafe.dao.factory.DAOFactory;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.entity.dto.UserContainer;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.exception.InvalidDataException;
import com.bogdevich.cafe.exception.ReceiverException;
import com.bogdevich.cafe.exception.TransactionException;
import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.transaction.ITransactionManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bogdevich.cafe.constant.ErrorMessage.*;
import static com.bogdevich.cafe.util.PasswordUtil.*;
import static com.bogdevich.cafe.util.Validator.*;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private ITransactionManager transactionManager;
    private DAOFactory daoFactory;

    public UserServiceImpl(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    public Optional<User> login(String login, String password) throws ReceiverException {
        LOGGER.log(Level.DEBUG, "Try to login: ["+login+", "+password+"]");
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            return transactionManager
                    .executeInTransaction(()-> userDAO.findUserByLogin(login))
                    .filter(user1 -> checkPassword(password, user1.getPassword()));
        } catch (TransactionException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            throw new ReceiverException(ex);
        }
    }

    @Override
    public Optional<UserContainer> register(String login, String password, String number, String street, String house, String flat)
            throws ReceiverException, InvalidDataException {

        String validLogin = parseLogin(login);
        String validPassword = parsePasword(password);
        int validNumber = parseNumber(number);
        String validStreet = parseStreet(street);
        String validHouse = parseHouse(house);
        int validFlat = parseFlat(flat);

        UserDAO userDAO = daoFactory.getUserDAO();
        UserInfoDAO userInfoDAO = daoFactory.getUserInfoDAO();
        try {
            if (transactionManager
                    .executeInTransaction(()-> userDAO.findUserByLogin(login))
                    .isPresent()) {
                throw new InvalidDataException(ErrorMessage.LOGIN_EXISTS);
            }
            String hashedPassword = hashPassword(validPassword);
            return transactionManager
                    .executeInTransaction(() -> {
                        User user = userDAO.create(validLogin, hashedPassword, BigDecimal.ZERO, Role.CUSTOMER).orElseThrow(DAOException::new);
                        UserInfo userInfo = userInfoDAO.create(validNumber, validStreet, validHouse, validFlat, user.getId()).orElseThrow(DAOException::new);
                        return Optional.of(new UserContainer(user, userInfo));
                    });
        } catch (TransactionException | IllegalArgumentException ex) {
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
