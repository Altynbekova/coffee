package com.altynbekova.top.practice.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DbUtil {
    private static final Logger LOGGER = LogManager.getLogger(DbUtil.class);
    private static final String URL = "jdbc:postgresql://localhost:5433/coffee_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";
    private static final int POOL_SIZE = 10;

    private static BlockingQueue<Connection> connectionPool;

    static {
        fillConnectionPool();
    }

    private static void fillConnectionPool() {
        connectionPool = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                Object proxyConnection = Proxy.newProxyInstance(
                        DbUtil.class.getClassLoader(),
                        new Class[]{Connection.class},
                        (proxy, method, args) ->
                                "close".equals(method.getName()) ? connectionPool.offer((Connection)proxy) :
                                        method.invoke(connection, args)
                );
                connectionPool.offer((Connection) proxyConnection);
            } catch (SQLException e) {
                LOGGER.error("fillConnectionPool() failed", e);
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
