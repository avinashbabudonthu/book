package com.postgresql;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class PostgreSQLHikariCPTest {

    @Test
    void datasource(){
        HikariDataSource hikariDataSource = PostgreSQLDataSource.dataSource();
        Properties properties = hikariDataSource.getDataSourceProperties();
        properties.list(System.out);
        log.info("pool size={}", hikariDataSource.getMaximumPoolSize());
        log.info("pool name={}", hikariDataSource.getPoolName());
        hikariDataSource.close();
    }

    @Test
    void getConnection(){
        try {
            Connection connection1 = PostgreSQLDataSource.getConnection();
            log.info("connection1={}", connection1);

            Connection connection2 = PostgreSQLDataSource.getConnection();
            log.info("connection2={}", connection2);

            Connection connection3 = PostgreSQLDataSource.getConnection();
            log.info("connection3={}", connection3);

            Connection connection4 = PostgreSQLDataSource.getConnection();
            log.info("connection4={}", connection4);
        } catch (SQLException e) {
            log.error("Exception", e);
        }
    }

    @DisplayName("Execute query")
    @Test
    void executeQuery(){
        String sqlQuery2 = "SELECT * FROM EMP";
        try (Connection connection = PostgreSQLDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlQuery2);
             ResultSet resultset = ps.executeQuery()) {
            while (resultset.next()){
                log.info("empno={}, ename={}, deptno={}, job={}, sal={}, comm={}, mgr={}, hiredate={}, active={}",
                        resultset.getString("empno"),
                        resultset.getString("ename"),
                        resultset.getString("deptno"),
                        resultset.getString("job"),
                        resultset.getString("sal"),
                        resultset.getString("comm"),
                        resultset.getString("mgr"),
                        resultset.getString("hiredate"),
                        resultset.getString("active")
                );
            }
        } catch (SQLException e) {
            log.error("Exception", e);
        }
    }

}
