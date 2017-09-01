package com.bogdevich.cafe.transaction.impl;

import com.bogdevich.cafe.connectionpool.ConnectionPool;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.exception.TransactionException;
import com.bogdevich.cafe.transaction.IDataSource;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.function.ExecutableTransaction;

import java.sql.Connection;
import java.util.Optional;

public class TransactionManager implements ITransactionManager, IDataSource {
    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManager();
    /**
     *
     */
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    public static TransactionManager getInstance() {
        return TRANSACTION_MANAGER;
    }

    private TransactionManager() {
    }

    /**
     *
     * @param unit
     * @param <T>
     * @return
     * @throws TransactionException
     */
    @Override
    public <T> T executeInTransaction(ExecutableTransaction<T> unit) throws TransactionException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        CONNECTION_THREAD_LOCAL.set(connection);
        T result;
        try {
            setAutoCommit(connection, false);
            result = unit.execute();
            commit(connection);
            return result;
        } catch (DAOException ex) {
            rollback(connection);
            throw new TransactionException(ex);
        } finally {
            setAutoCommit(connection, true);
            CONNECTION_THREAD_LOCAL.remove();
            pool.retrieveConnection(connection);
        }
    }

/*    @Override
    public void executeWithoutTransaction(ExecutableTransaction unit) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        CONNECTION_THREAD_LOCAL.set(connection);
        unit.execute(object, objects);
        CONNECTION_THREAD_LOCAL.remove();
        pool.retrieveConnection(connection);
    }*/

    /**
     *
     * @return
     * @throws DAOException
     */
    @Override
    public Connection getConnection() throws DAOException {
        return Optional
                .ofNullable(CONNECTION_THREAD_LOCAL.get())
                .orElseThrow(() -> new DAOException("No connection in local thread"));
    }
}
