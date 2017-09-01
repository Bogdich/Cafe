package com.bogdevich.cafe.transaction;

import com.bogdevich.cafe.exception.TransactionException;
import com.bogdevich.cafe.transaction.function.ExecutableTransaction;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public interface ITransactionManager {
    Logger LOGGER = LogManager.getLogger();

    <T> T executeInTransaction(ExecutableTransaction<T> unit) throws TransactionException;

    default void setAutoCommit(Connection connection, boolean b) {
        try {
            connection.setAutoCommit(b);
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex);
        }
    }

    default void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex);
        }
    }

    default void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, ex.getMessage(), ex);
        }
    }
}
