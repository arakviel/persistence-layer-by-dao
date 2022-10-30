package com.arakviel.persistence;

import com.arakviel.persistence.exeption.connection.BlockingQueueTakeException;
import com.arakviel.persistence.exeption.connection.ConnectionException;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Helper connection class to embedded h2 database, witch implements 'Object Pool' pattern. At
 * launching, defined number of connections is created {@link ConnectionPool#POOL_SIZE}.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Object_pool_pattern">Wikipedia 'Object Pool'</a>
 */
public final class ConnectionPool {

    /** Relative path to the database file. */
    private static final String DB_PATH = Path.of(".", "db-h2", "blog").toString();

    private static final String URL = "jdbc:h2:file:%s".formatted(DB_PATH);
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final int POOL_SIZE = 10;

    private static BlockingQueue<Connection> pool;
    /** Using, only to close all not modified connections. */
    private static Set<Connection> sourceConnections;

    // TODO: Rewrite static init. Show by GUI in future.
    static {
        try {
            initConnectionPool();
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
    }

    private ConnectionPool() {}

    public static Connection get() {

        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BlockingQueueTakeException(
                    "Помилка, при багатопотоковому доступі до підключень, або немає вільних"
                            + " підключень.");
        }
    }

    /**
     * Close all connection pool, when program is an exit. Usage example:
     *
     * <pre>
     * public static void main(String[] args) {
     *      try {
     *          // some code...
     *      } finally {
     *          <b>ConnectionPool.closePool();</b>
     *      }
     *  }
     * </pre>
     *
     * @throws ConnectionException fail on db connection close.
     */
    public static void closePool() throws ConnectionException {
        try {
            for (Connection sourceConnection : sourceConnections) {
                sourceConnection.close();
            }
        } catch (SQLException e) {
            throw new ConnectionException("Помилка, при закритті підключення до БД.");
        }
    }

    private static Connection open() throws ConnectionException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new ConnectionException("Помилка, під час відкриття підключення до БД.");
        }
    }

    /**
     * Initialization two connection pool, where pool modified close method by Proxy pattern. Close
     * method return connection to pool, not close it. Second {@link #sourceConnections}, need for
     * connection closing by {@link #closePool()}.
     *
     * @throws ConnectionException if fail on open connection.
     * @see <a href="https://refactoring.guru/design-patterns/proxy">Proxy Refactoring Guru</a>
     */
    private static void initConnectionPool() throws ConnectionException {
        pool = new ArrayBlockingQueue<>(POOL_SIZE);
        sourceConnections = new HashSet<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = open();

            Connection proxyConnection =
                    (Connection)
                            Proxy.newProxyInstance(
                                    ConnectionPool.class.getClassLoader(),
                                    new Class[] {Connection.class},
                                    ((proxy, method, args) ->
                                            method.getName().equals("close")
                                                    ? pool.add((Connection) proxy)
                                                    : method.invoke(connection, args)));
            pool.add(proxyConnection);
            sourceConnections.add(connection);
        }
    }
}
