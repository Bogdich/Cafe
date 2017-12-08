package com.bogdevich.cafe.service;

import com.bogdevich.cafe.entity.bean.Dish;
import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DishService {

    void createDish(String name, String price, String weight,
                    String description, String picture, String category)
            throws ServiceException, InvalidDataException;

    void createOrderDishRecord(String orderID, String dishID, String quantity)
            throws ServiceException, InvalidDataException;

    void createUserDishRecord(int userID, String dishID, String quantity)
            throws ServiceException, InvalidDataException;

    List<Dish> findAllDishes()
            throws ServiceException, InvalidDataException;

    List<Dish> findDishByCategoryId(String categoryID)
            throws ServiceException, InvalidDataException;

    List<Dish> findDishByCategoryName(String categoryName)
            throws ServiceException, InvalidDataException;

    List<Dish> findDishByOrderId(String orderID)
            throws ServiceException, InvalidDataException;

    List<Dish> findDishFromShoppingCartByUserId(String userID)
            throws ServiceException, InvalidDataException;

    Optional<Dish> findDishById(String dishID)
            throws ServiceException, InvalidDataException;

    Optional<Dish> findDishByName(String name)
            throws ServiceException, InvalidDataException;

    Map<Dish, Integer> findUserDishMap(int userID)
            throws ServiceException, InvalidDataException;

    Map<Dish, Integer> findOrderDishMap(String orderID)
            throws ServiceException, InvalidDataException;

    Optional<Dish> updateDish(String name, String price, String weight,
                              String description, String picture,
                              String category, String dishID)
            throws ServiceException, InvalidDataException;

    void updateOrderDishRecord(String orderID, String dishID, String quantity)
            throws ServiceException, InvalidDataException;

    void updateUserDishRecord(int userID, String dishID, String quantity)
            throws ServiceException, InvalidDataException;

    void deleteDish(String dishID) throws ServiceException, InvalidDataException;

    void deleteOrderDishRecords(String orderID)
            throws ServiceException, InvalidDataException;

    void deleteUserDishRecord(int userID, String dishID)
            throws ServiceException, InvalidDataException;

    void addDishToShoppingCart() throws ServiceException, InvalidDataException;

    void changeDishQuantityInShoppingCart(int userID, String dishID, String quantity)
            throws ServiceException, InvalidDataException;

    void changeDishQuantityInShoppingCart(HttpSession session, String dishID, String quantity)
            throws ServiceException, InvalidDataException;

    void deleteDishFromShoppingCart(int userID, String dishID)
            throws ServiceException, InvalidDataException;

    void deleteDishFromShoppingCart(HttpSession session, String dishID)
            throws ServiceException, InvalidDataException;

    Integer getShoppingCartSize(int userID)
            throws ServiceException, InvalidDataException;

    Integer getShoppingCartSize(HttpSession session)
            throws ServiceException, InvalidDataException;

    BigDecimal getShoppingCartTotalCost(int userID)
            throws ServiceException, InvalidDataException;

    BigDecimal getShoppingCartTotalCost(HttpSession session)
            throws ServiceException, InvalidDataException;

    Map<Dish, Integer> getMapAttributeFromSession(HttpSession session);
}
