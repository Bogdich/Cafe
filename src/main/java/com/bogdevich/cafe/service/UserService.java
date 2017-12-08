package com.bogdevich.cafe.service;

import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.entity.wrapper.UserWrapper;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> authorize(String login, String password) throws ServiceException, InvalidDataException;

    Optional<UserWrapper> register(String login, String password,
                                   String Number, String street,
                                   String house, String flat) throws ServiceException, InvalidDataException;

    boolean loginExist(String login) throws ServiceException, InvalidDataException;

    List<User> findAllUsers() throws ServiceException, InvalidDataException;

    List<User> findUserByRoleId(Role role) throws ServiceException, InvalidDataException;

    Optional<UserInfo> findUserInfo(int userID) throws ServiceException, InvalidDataException;

    void updateLogin(int userID, String login) throws ServiceException, InvalidDataException;

    boolean updatePassword(int userID, String oldPassword,
                        String newPassword) throws ServiceException, InvalidDataException;

}
