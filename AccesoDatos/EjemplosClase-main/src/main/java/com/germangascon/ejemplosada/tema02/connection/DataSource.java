package com.germangascon.ejemplosada.tema02.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * <p><strong>DataSource</strong></p>
 * <p>Descripción</p>
 * License: 🅮 Public Domain<br />
 * Created on: 2025-11-13<br />
 *
 * @author Germán Gascón <ggascon@gmail.com>
 * @version 0.0.1
 * @since 0.0.1
 **/
public class DataSource {
    private final String url;
    private final String user;
    private final String password;

    public enum Driver {
        MYSQL, POSTGRESQL;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public DataSource(Driver driver, String host, int port, String database, String user, String password) {
        this.url = "jdbc:" + driver + "://" + host + ":" + port + "/" + database;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
