package com.postgresql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class PostgreSQLDataSource {

    private PostgreSQLDataSource() {
    }

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    /**
     * Refer below link for detailed explanation of each property:
     * https://github.com/brettwooldridge/HikariCP
     */
    static {
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/practice");
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("practice");
        config.setPassword("practice");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setAutoCommit(true);
        config.setPoolName("postgresql-connection-pool");
        config.setMaximumPoolSize(20);
        config.setConnectionTimeout(1000 * 5); // connection timeout in milli seconds
        config.setConnectionInitSql("select curdate()");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static HikariDataSource dataSource(){
        return dataSource;
    }

}
