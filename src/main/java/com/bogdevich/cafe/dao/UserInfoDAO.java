package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserInfoDAO extends BaseDAO{
    Optional<UserInfo> createUserInfo(int Number, String street, String house, int flat, int userID) throws DAOException;

    List<UserInfo> findAllInfos() throws DAOException;
    List<UserInfo> findUserInfoByUserId(int userID) throws DAOException;
    Optional<UserInfo> findUserInfoByOrderId(int orderID) throws DAOException;
    Optional<UserInfo> findUserInfoById(int id) throws DAOException;

    boolean updateUserInfo(UserInfo userinfo) throws DAOException;

    boolean deleteUserInfo(UserInfo userinfo) throws DAOException;
}
