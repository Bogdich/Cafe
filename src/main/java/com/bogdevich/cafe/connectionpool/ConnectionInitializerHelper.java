package com.bogdevich.cafe.connectionpool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.bogdevich.cafe.connectionpool.ConnectionParameter.*;

/**
 * Created by Grodno on 24.07.2017.
 */
class ConnectionInitializerHelper {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionInitializerHelper.class);
    private static final ConnectionInitializerHelper INSTANCE = new ConnectionInitializerHelper();

    static ConnectionInitializerHelper getInstance(){
        return INSTANCE;
    }

    private final Properties connectionInfo;

    private ConnectionInitializerHelper() throws RuntimeException {
        connectionInfo = new Properties();
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES_PATH);
            Class.forName(resourceBundle.getString(DB_DRIVER));
            connectionInfo.put(DB_URL, resourceBundle.getString(DB_URL));
            connectionInfo.put(DB_USER, resourceBundle.getString(DB_USER));
            connectionInfo.put(DB_PASSWORD, resourceBundle.getString(DB_PASSWORD));
            connectionInfo.put(DB_POOL_SIZE, resourceBundle.getString(DB_POOL_SIZE));
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.FATAL, "Class.forName(resourceBundle.getString(DB_DRIVER)): The class cannot be located");
        } catch (MissingResourceException ex) {
            LOGGER.log(Level.FATAL, "No resource bundle for the specified base name can be found");
            throw new RuntimeException(ex);
        } catch (ClassCastException ex){
            LOGGER.log(Level.FATAL, "Object found for the given key is not a string");
            throw new RuntimeException(ex);
        }
    }

    String getURL() {
        return connectionInfo.getProperty(DB_URL);
    }

    String getUserName() {
        return connectionInfo.getProperty(DB_USER);
    }

    String getPassword() {
        return connectionInfo.getProperty(DB_PASSWORD);
    }

    int getPoolSize() {
        try {
            return Integer.parseInt(connectionInfo.getProperty(DB_URL));
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.ERROR, "The string in \"resourceBundle.getString(DB_POOL_SIZE)\" does not contain a parsable integer");
            return DB_DEFAULT_POOL_SIZE;
        }
    }

    Properties getInfo() {
        Properties props = new Properties();
        props.put("user", connectionInfo.getProperty(DB_USER));
        props.put("password", connectionInfo.getProperty(DB_PASSWORD));
        return props;
    }

}
