package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.Order;
import com.bogdevich.cafe.entity.bean.UserInfo;
import com.bogdevich.cafe.exception.DAOException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Optional<Order> create(BigDecimal total, Date orderTime, UserInfo userInfo, HashMap<Dish, Integer> dishQuantityMap)
            throws DAOException;

    List<Order> findAll() throws DAOException;
    List<Order> findOrderByUserId(int userID) throws DAOException;
    List<Order> findOrderByDishId(int dishID) throws DAOException;
    List<Dish> findOrderByUserInfo(UserInfo userInfo) throws DAOException;
    Optional<Order> findOrderById(int orderID) throws DAOException;

    boolean update(Order order) throws DAOException;

    boolean delete(int orderID) throws DAOException;
}
