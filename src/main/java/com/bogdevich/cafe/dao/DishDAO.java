package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.entity.bean.Dish;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DishDAO extends BaseDAO {

    boolean createDish(Dish dish) throws DAOException;

    void createOrderDishRecord(int orderID, int dishID, int quantity) throws DAOException;

    void createUserDishRecord(int userID, int dishID, int quantity) throws DAOException;

    List<Dish> findAllDishes() throws DAOException;

    List<Dish> findDishByCategoryId(int categoryID) throws DAOException;

    List<Dish> findDishByOrderId(int orderID) throws DAOException;

    List<Dish> findDishFromSCByUserId(int userID) throws DAOException;

    Optional<Dish> findDishById(int dishID) throws DAOException;

    Optional<Dish> findDishByName(String name) throws DAOException;

    Map<Dish, Integer> findUserDishMap(int userID) throws DAOException;

    Map<Dish, Integer> findOrderDishMap(int orderID) throws DAOException;

    boolean updateDish(Dish dish) throws DAOException;

    boolean updateOrderDishRecord(int orderID, int dishID, int quantity) throws DAOException;

    boolean updateUserDishRecord(int userID, int dishID, int quantity) throws DAOException;

    boolean deleteDish(int dishID) throws DAOException;

    boolean deleteOrderDishRecords(int orderID) throws DAOException;

    boolean deleteUserDishRecord(int userID, int dishID) throws DAOException;
}
