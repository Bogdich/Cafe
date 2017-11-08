package com.bogdevich.cafe.dao.function;

import java.sql.SQLException;

@FunctionalInterface
public interface DAOBiConsumer<T, V> {
    void accept(T t, V v) throws SQLException;
}
