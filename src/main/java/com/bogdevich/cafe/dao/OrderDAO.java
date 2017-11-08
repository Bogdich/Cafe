package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO extends BaseDAO {

    int createOrder(Order order) throws DAOException;

    List<Order> findAll() throws DAOException;

    List<Order> findOrderByUserId(int userID) throws DAOException;

    List<Order> findOrderByDishId(int dishID) throws DAOException;

    List<Dish> findOrderByAddress(String street, String house, int flat) throws DAOException;

    List<Dish> findOrderByPhoneNumber(int number) throws DAOException;

    Optional<Order> findOrderById(int orderID) throws DAOException;

    boolean updateOrder(Order order) throws DAOException;

    boolean deleteOrder(int orderID) throws DAOException;
}
