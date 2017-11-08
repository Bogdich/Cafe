package com.bogdevich.cafe.transaction.function;

import com.bogdevich.cafe.dao.exception.DAOException;

@FunctionalInterface
public interface ExecutableTransaction<T> {

    /**
     *
     * @return
     * @throws DAOException
     */
    T perform() throws DAOException;
}
