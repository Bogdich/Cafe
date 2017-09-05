package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserInfoDAO {
    Optional<UserInfo> create(int Number, String street, String house, int flat, int userID) throws DAOException;

    List<UserInfo> findAll() throws DAOException;
    List<UserInfo> findUserInfoByUserId(int userID) throws DAOException;
    Optional<UserInfo> findUserInfoByOrderId(int orderID) throws DAOException;
    Optional<UserInfo> findUserInfoById(int id) throws DAOException;

    boolean update(UserInfo userinfo) throws DAOException;

    boolean delete(UserInfo userinfo) throws DAOException;
}
