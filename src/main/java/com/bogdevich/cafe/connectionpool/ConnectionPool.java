package com.bogdevich.cafe.connectionpool;

        import org.apache.logging.log4j.Level;
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;

        import java.sql.DriverManager;
        import java.sql.SQLException;
        import java.util.concurrent.ArrayBlockingQueue;
        import java.util.concurrent.BlockingQueue;

/**
 * Created by Grodno on 23.07.2017.
 */
public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> busyConnections;

    private static class ConnectionPoolHolder{
        private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();
    }

    public static ConnectionPool getInstance(){
        return ConnectionPoolHolder.CONNECTION_POOL;
    }

    private ConnectionPool(){
        ConnectionInitializerHelper connectionHelper = ConnectionInitializerHelper.getInstance();
        freeConnections = new ArrayBlockingQueue<>(connectionHelper.getPoolSize());
        busyConnections = new ArrayBlockingQueue<>(connectionHelper.getPoolSize());
        try {
            for (int i=0; i<connectionHelper.getPoolSize(); i++){
                ProxyConnection connection = new ProxyConnection(
                        DriverManager.getConnection(
                                connectionHelper.getURL(),
                                connectionHelper.getInfo()
                        )
                );
                freeConnections.add(connection);
            }

            LOGGER.log(Level.INFO, "Connection pool has been initialised");
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "A database access error");
            throw new RuntimeException(ex);
        }

    }

    public ProxyConnection takeConnection() {
        try{
            ProxyConnection connection = freeConnections.take();
            busyConnections.add(connection);
            LOGGER.log(Level.INFO, String.join(": ", "Connection was taken", String.valueOf(connection.hashCode())));
            return connection;
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Interrupted while waiting for connection", ex);
        }
        return takeConnection();
    }

    void retrieveConnection(ProxyConnection connection) {
        if (busyConnections.remove(connection)) {
            freeConnections.add(connection);
            LOGGER.log(Level.INFO, String.join(": ","Connection was retrieved", String.valueOf(connection.hashCode())));
        } else {
            LOGGER.log(Level.ERROR, "Trying to remove wild connection");
        }
    }

    public void dispose() {
        try {
            for (int i=0; i<freeConnections.size(); i++) {
                freeConnections.take().realClose();
            }
            LOGGER.log(Level.INFO, "Connections have been successfully closed");
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Interrupted while waiting for connection", ex);
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "A database access error occurs while closing connection", ex);
        }
    }
}
