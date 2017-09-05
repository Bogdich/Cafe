package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    Optional<User> create(int number, String password, String name, BigDecimal balance, Role role) throws DAOException;

    List<User> findAll() throws DAOException;
    Optional<User> findUserByNumber(int number) throws DAOException;
    Optional<User> findUserById(int id) throws DAOException;

    boolean update(User user) throws DAOException;
    boolean updateBalance(BigDecimal balance) throws DAOException;
    boolean updatePassword(String password) throws DAOException;
    boolean updateNumber(int number) throws DAOException;

    boolean delete(User user) throws DAOException;
}
