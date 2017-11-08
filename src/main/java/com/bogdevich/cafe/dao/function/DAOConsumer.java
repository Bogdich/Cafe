package com.bogdevich.cafe.dao.function;

import java.sql.SQLException;

@FunctionalInterface
public interface DAOConsumer<T>{
    void accept(T t) throws SQLException;
}
