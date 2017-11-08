package com.bogdevich.cafe.dao.function;

import java.sql.SQLException;

@FunctionalInterface
public interface DAOFunction<T, R> {
    R apply(T t) throws SQLException;
}
