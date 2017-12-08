package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.type.Role;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDAO extends BaseDAO{
    Optional<User> createUser(String login, String password, BigDecimal balance, Role role) throws DAOException;

    List<User> findAllUsers() throws DAOException;
    Optional<User> findUserByLogin(String login) throws DAOException;
    Optional<User> findUserById(int id) throws DAOException;
    List<User> findUserByRoleId(int roleID) throws DAOException;

    boolean updateUser(User user) throws DAOException;

    boolean deleteUser(User user) throws DAOException;
}
