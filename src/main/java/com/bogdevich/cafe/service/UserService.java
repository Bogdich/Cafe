package com.bogdevich.cafe.service;

import com.bogdevich.cafe.entity.bean.User;
import com.bogdevich.cafe.entity.dto.UserContainer;
import com.bogdevich.cafe.entity.type.Role;
import com.bogdevich.cafe.exception.InvalidDataException;
import com.bogdevich.cafe.exception.ReceiverException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String login, String password)
            throws ReceiverException, InvalidDataException;
    Optional<UserContainer> register(String login, String password, String Number, String street, String house, String flat)
            throws ReceiverException, InvalidDataException;
    List<User> findAllUsers()
            throws ReceiverException, InvalidDataException;
}
