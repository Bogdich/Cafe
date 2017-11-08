package com.bogdevich.cafe.dao;

import com.bogdevich.cafe.dao.exception.DAOException;
import com.bogdevich.cafe.dao.function.DAOConsumer;
import com.bogdevich.cafe.dao.function.DAOFunction;

import java.sql.*;
import java.util.*;

public interface BaseDAO {

    default int create(Connection connection, String query,
                       DAOConsumer<PreparedStatement> function,
                       boolean returnGeneratedKey) throws DAOException {
        int flag = returnGeneratedKey? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS;
        try(PreparedStatement statement = connection.prepareStatement(query, flag)){
            function.accept(statement);
            int rows = statement.executeUpdate();
            ResultSet rs = returnGeneratedKey ?
                    statement.getGeneratedKeys() :
                    null;
            boolean resultCondition = returnGeneratedKey ?
                    (rows == 1 && rs.next()) :
                    (rows == 1);
            if (resultCondition) {
                return returnGeneratedKey ? rs.getInt(1) : 1;
            } else {
                return 0;
            }
        } catch (SQLException ex){
            throw new DAOException(ex);
        }
    }

    default <T> Optional<T> findRecord(Connection connection, String query,
                                       DAOConsumer<PreparedStatement> consumer,
                                       DAOFunction<ResultSet, T> function) throws DAOException {
        T object = null;
        try(PreparedStatement statement = connection.prepareStatement(query)){
            consumer.accept(statement);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                object = function.apply(rs);
            }
            return Optional.ofNullable(object);
        }catch (SQLException ex){
            throw new DAOException(ex);
        }
    }

    default <T> List<T> findRecordList(Connection connection, String query,
                                       DAOConsumer<PreparedStatement> consumer,
                                       DAOFunction<ResultSet, T> function) throws DAOException {
        T object;
        List<T> list = new LinkedList<>();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            consumer.accept(statement);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                object = function.apply(rs);
                list.add(object);
            }
            return list;
        }catch (SQLException ex){
            throw new DAOException(ex);
        }
    }

    default <K,V> Map<K,V> findRecordMap(Connection connection, String query,
                                         DAOConsumer<PreparedStatement> consumer, DAOFunction<ResultSet,K> keyFunction,
                                         DAOFunction<ResultSet,V> valueFunction) throws DAOException {
        K key;
        V value;
        Map<K,V> map = new HashMap<>();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            consumer.accept(statement);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                key = keyFunction.apply(rs);
                value = valueFunction.apply(rs);
                map.put(key, value);
            }
            return map;
        }catch (SQLException ex){
            throw new DAOException(ex);
        }
    }

    default <T> List<T> findAllRecords(Connection connection, String query,
                                       DAOFunction<ResultSet, T> function) throws DAOException {
        T object;
        List<T> list = new LinkedList<>();
        try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                object = function.apply(rs);
                list.add(object);
            }
            return list;
        }catch (SQLException ex){
            throw new DAOException(ex);
        }
    }

    default boolean update(Connection connection, String query,
                           DAOConsumer<PreparedStatement> consumer) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(query)){
            consumer.accept(statement);
            return (statement.executeUpdate() == 1);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    default boolean delete(Connection connection, String query,
                           DAOConsumer<PreparedStatement> consumer) throws DAOException {
        try(PreparedStatement statement = connection.prepareStatement(query)){
            consumer.accept(statement);
            return (statement.executeUpdate() == 1);
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

}
