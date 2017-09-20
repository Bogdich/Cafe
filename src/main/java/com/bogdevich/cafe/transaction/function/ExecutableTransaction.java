package com.bogdevich.cafe.transaction.function;

import com.bogdevich.cafe.exception.DAOException;

@FunctionalInterface
public interface ExecutableTransaction<T> {

    /**
     *
     * @return
     * @throws DAOException
     */
    T perform() throws DAOException;
}
