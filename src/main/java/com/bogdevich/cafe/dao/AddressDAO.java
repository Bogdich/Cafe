package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.entity.bean.Address;
import com.bogdevich.cafe.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface AddressDAO {
    Optional<Address> create(String street, String house, String flat, int userID) throws DAOException;

    List<Address> findAll() throws DAOException;
    Optional<Address> findAddressByUserId(int userID) throws DAOException;
    Optional<Address> findAddressByOrderId(int orderID) throws DAOException;
    Optional<Address> findAddressById(int id) throws DAOException;

    boolean update(Address address) throws DAOException;

    boolean delete(Address address) throws DAOException;
}
