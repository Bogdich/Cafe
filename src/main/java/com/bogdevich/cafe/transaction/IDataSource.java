package com.bogdevich.cafe.transaction;

import com.bogdevich.cafe.dao.exception.DAOException;

import java.sql.Connection;

public interface IDataSource {
    Connection getConnection() throws DAOException;
}
