package com.bogdevich.cafe.connectionpool;

        import com.bogdevich.cafe.exception.ConnectionPoolException;
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
            for (int i=0; i<freeConnections.size(); i++){
                ProxyConnection connection = new ProxyConnection(
                        DriverManager.getConnection(
                                connectionHelper.getURL(),
                                connectionHelper.getInfo()
                        )
                );
                freeConnections.add(connection);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.FATAL, "A database access error");
            throw new RuntimeException(ex);
        }
    }

    public ProxyConnection takeConnection() throws ConnectionPoolException {
        try{
            ProxyConnection connection = freeConnections.take();
            busyConnections.add(connection);
            return connection;
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Interrupted while waiting for connection");
            throw new ConnectionPoolException(ex);
        }
    }

    public void retrieveConnection(ProxyConnection connection) throws ConnectionPoolException{
        if (busyConnections.remove(connection)) {
            freeConnections.add(connection);
        } else {
            LOGGER.log(Level.ERROR, "Trying to remove wild connection");
            throw new ConnectionPoolException();
        }
    }

    public void dispose() throws ConnectionPoolException{
        try {
            for (int i=0; i<freeConnections.size(); i++) {
                freeConnections.take().close();
            }
            LOGGER.log(Level.INFO, "Connections have been successfully closed");
        } catch (InterruptedException ex) {
            LOGGER.log(Level.ERROR, "Interrupted while waiting for connection");
            throw new ConnectionPoolException(ex);
        } catch (SQLException ex) {
            LOGGER.log(Level.ERROR, "A database access error occurs while closing connection");
            throw new ConnectionPoolException(ex);
        }
    }
}
