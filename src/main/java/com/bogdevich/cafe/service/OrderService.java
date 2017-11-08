package com.bogdevich.cafe.service;

import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.entity.bean.Order;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> createOrder(String number, String street,
                                String house, String flat,
                                int userID) throws ServiceException, InvalidDataException;

    List<Order> findAll() throws ServiceException;

    List<Order> findOrderByUserId(int userID) throws ServiceException, InvalidDataException;

    List<Order> findOrderByUserId(String userID) throws ServiceException, InvalidDataException;

    List<Order> findOrderByDishId(String dishID) throws ServiceException, InvalidDataException;

    List<Dish> findOrderByPhoneNumber(String phoneNumber) throws ServiceException, InvalidDataException;

    List<Dish> findOrderByAddress(String street, String house,
                                  String flat) throws ServiceException, InvalidDataException;

    Optional<Order> findOrderById(String orderID) throws ServiceException, InvalidDataException;

    boolean updateOrder(Order order) throws ServiceException, InvalidDataException;

    void deleteOrder(String orderID) throws ServiceException, InvalidDataException;
}
