package com.bogdevich.cafe.service.impl;

import com.bogdevich.cafe.command.constant.ErrorMessage;
import com.bogdevich.cafe.dao.UserDAO;
import com.bogdevich.cafe.dao.UserInfoDAO;
import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.dao.factory.DAOFactory;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.entity.wrapper.UserWrapper;
import com.bogdevich.cafe.service.UserService;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;
import com.bogdevich.cafe.service.util.PasswordUtil;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.exception.TransactionException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bogdevich.cafe.service.util.PasswordUtil.checkPassword;
import static com.bogdevich.cafe.service.util.PasswordUtil.hashPassword;
import static com.bogdevich.cafe.service.util.Validator.*;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private ITransactionManager transactionManager;
    private DAOFactory daoFactory;

    public UserServiceImpl(ITransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        daoFactory = DAOFactory.getInstance();
    }

    @Override
    public Optional<User> authorize(String login, String password)
            throws ServiceException, InvalidDataException {
        LOGGER.log(Level.DEBUG, "Try to login: ["+login+", "+password+"]");
        UserDAO userDAO = daoFactory.getUserDAO();
        try {

            String validLogin = parseLogin(login);
            String validPassword = parsePassword(password);

            return transactionManager
                    .executeInTransaction(()-> userDAO.findUserByLogin(validLogin))
                    .filter(user1 -> checkPassword(validPassword, user1.getPassword()));
        } catch (TransactionException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage());
            throw new ServiceException(ex);
        }
    }

    @Override
    public Optional<UserWrapper> register(String login, String password, String number, String street, String house, String flat)
            throws ServiceException, InvalidDataException {

        String validLogin = parseLogin(login);
        String validPassword = parsePassword(password);
        int validNumber = parsePhoneNumber(number);
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
                        User user = userDAO
                                .createUser(validLogin, hashedPassword, BigDecimal.ZERO, Role.CUSTOMER)
                                .orElseThrow(DAOException::new);
                        UserInfo userInfo = userInfoDAO
                                .createUserInfo(validNumber, validStreet, validHouse, validFlat, user.getId())
                                .orElseThrow(DAOException::new);
                        return Optional.of(new UserWrapper(user, userInfo));
                    });
        } catch (TransactionException | IllegalArgumentException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            return transactionManager
                    .executeInTransaction(userDAO::findAllUsers)
                    .stream()
                    .sorted(Comparator.comparing(User::getLogin))
                    .collect(Collectors.toList());
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean loginExist(String login) throws ServiceException, InvalidDataException {
        String validLogin = parseLogin(login);
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            return transactionManager
                    .executeInTransaction(() -> userDAO.findUserByLogin(validLogin))
                    .isPresent();
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public void updateLogin(int userID, String login) throws ServiceException, InvalidDataException {
        String validLogin = parseLogin(login);
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            transactionManager.executeInTransaction(() -> {
                User user = userDAO
                        .findUserById(userID)
                        .orElseThrow(() -> new DAOException("Trying to update login of a non existing user."));
                user.setLogin(validLogin);
                boolean updated = userDAO.updateUser(user);
                if (!updated) {
                    throw new DAOException("Exception while updating user.");
                }
                return Optional.empty();
            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }

    @Override
    public boolean updatePassword(int userID, String oldPassword,
                               String newPassword) throws ServiceException, InvalidDataException {
        String validOldPwd = parsePassword(oldPassword);
        String validNewPwd = parsePassword(newPassword);
        UserDAO userDAO = daoFactory.getUserDAO();
        try {
            return transactionManager.executeInTransaction(() -> {
                boolean updated;
                User user = userDAO
                        .findUserById(userID)
                        .orElseThrow(() -> new DAOException("Trying to update password of a non existing user."));
                if (PasswordUtil.checkPassword(validOldPwd, user.getPassword())){
                    String newHashedPwd = PasswordUtil.hashPassword(validNewPwd);
                    user.setPassword(newHashedPwd);
                    updated = userDAO.updateUser(user);
                    if (updated) {
                        return true;
                    } else {
                        throw new DAOException("Exception while trying to update user password.");
                    }
                } else {
                    return false;
                }

            });
        } catch (TransactionException ex) {
            throw new ServiceException(ex);
        }
    }
}
