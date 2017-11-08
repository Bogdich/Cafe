package com.bogdevich.cafe.command.function;


import com.bogdevich.cafe.service.exception.InvalidDataException;
import com.bogdevich.cafe.service.exception.ServiceException;

public interface CommandFunction<R, S, T, U> {
    R apply(S s, T t, U u) throws ServiceException, InvalidDataException;
}
