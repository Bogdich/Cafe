package com.bogdevich.cafe.transaction.impl;

import com.bogdevich.cafe.connectionpool.ConnectionPool;
import com.bogdevich.cafe.exception.DAOException;
import com.bogdevich.cafe.exception.TransactionException;
import com.bogdevich.cafe.transaction.IDataSource;
import com.bogdevich.cafe.transaction.ITransactionManager;
import com.bogdevich.cafe.transaction.function.ExecutableTransaction;
import org.apache.logging.log4j.Level;

import java.sql.Connection;
import java.util.Optional;

public class TransactionManager implements ITransactionManager, IDataSource {
    private static final TransactionManager TRANSACTION_MANAGER = new TransactionManager();
    /**
     * The field guarantees that every thread has its own exemplar of connection.
     */
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    public static TransactionManager getInstance() {
        return TRANSACTION_MANAGER;
    }

    private TransactionManager() {
    }

    /**
     * Current thread gets its own connection from @<code>ConnectionPool</code>.
     * The transaction occurs after the @<code>unit.perform()</code> is invoked.
     * @param unit  An exemplar of anonymous class or lambda expression.
     * @param <T>   Anonymous function returned type.
     * @return  Generic returned type.
     * @throws TransactionException When DAOException occurs.
     */
    @Override
    public <T> T executeInTransaction(ExecutableTransaction<T> unit) throws TransactionException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.takeConnection();
        CONNECTION_THREAD_LOCAL.set(connection);
        T result;
        try {
            setAutoCommit(connection, false);
            result = unit.perform();
            commit(connection);
            LOGGER.log(Level.DEBUG, "Returned result: "+result.toString());
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
