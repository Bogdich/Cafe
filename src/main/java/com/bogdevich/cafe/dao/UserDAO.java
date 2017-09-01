package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.enums.Role;
import com.bogdevich.cafe.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserDAO{
    List<User> findAll() throws DAOException;
    Optional<User> create(String login, String email, String password, Role role) throws DAOException;
    Optional<User> findUserByLogin(String login) throws DAOException;
    Optional<User> findUserById(int id) throws DAOException;
    boolean update(User user) throws DAOException;
    boolean delete(User user) throws DAOException;
}
