package com.oracle;

import com.mysql.MySQLDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Properties;

@Slf4j
public class OracleHikariCPTest {

    /**
     * [OracleHikariCPTest.datasource] - properties={prepStmtCacheSqlLimit=2048, cachePrepStmts=true, prepStmtCacheSize=250}
     * [OracleHikariCPTest.datasource] - pool size=20
     * [OracleHikariCPTest.datasource] - pool name=oracle-connection-pool
     */
    @Test
    void datasource() {
        HikariDataSource hikariDataSource = OracleDataSource.dataSource();
        Properties properties = hikariDataSource.getDataSourceProperties();
        log.info("properties={}", properties);

        log.info("pool size={}", hikariDataSource.getMaximumPoolSize());
        log.info("pool name={}", hikariDataSource.getPoolName());
        hikariDataSource.close();
    }

    /**
     * [OracleHikariCPTest.getConnection] - connection1=HikariProxyConnection@1674403916 wrapping oracle.jdbc.driver.T4CConnection@7a55af6b
     * [HikariPool$PoolEntryCreator.call] - oracle-connection-pool - Added connection oracle.jdbc.driver.T4CConnection@2c88b805
     * [OracleHikariCPTest.getConnection] - connection2=HikariProxyConnection@1658511941 wrapping oracle.jdbc.driver.T4CConnection@2c88b805
     * [HikariPool$PoolEntryCreator.call] - oracle-connection-pool - Added connection oracle.jdbc.driver.T4CConnection@284e59d3
     * [OracleHikariCPTest.getConnection] - connection3=HikariProxyConnection@1878992188 wrapping oracle.jdbc.driver.T4CConnection@284e59d3
     * [HikariPool.logPoolState] - oracle-connection-pool - Pool stats (total=3, active=3, idle=0, waiting=1)
     * [HikariPool$PoolEntryCreator.call] - oracle-connection-pool - Added connection oracle.jdbc.driver.T4CConnection@6e3724e3
     * [OracleHikariCPTest.getConnection] - connection4=HikariProxyConnection@1495161082 wrapping oracle.jdbc.driver.T4CConnection@6e3724e3
     */
    @Test
    void getConnection() {
        try {
            Connection connection1 = OracleDataSource.getConnection();
            log.info("connection1={}", connection1);

            Connection connection2 = OracleDataSource.getConnection();
            log.info("connection2={}", connection2);

            Connection connection3 = OracleDataSource.getConnection();
            log.info("connection3={}", connection3);

            Connection connection4 = OracleDataSource.getConnection();
            log.info("connection4={}", connection4);
        } catch (SQLException e) {
            log.error("Exception", e);
        }
    }

    /**
     * [OracleHikariCPTest.executeQuery] - date=2025-03-08 14:28:28
     */
    @DisplayName("Execute query")
    @Test
    void executeQuery() throws SQLException {
        String sqlQuery = "select sysdate from dual";

        Connection connection = OracleDataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sqlQuery);
        ResultSet resultset = ps.executeQuery();
        while (resultset.next()) {
            String date = resultset.getString("sysdate");
            log.info("date={}", date);
        }

        connection.close();
        ps.close();
        resultset.close();
    }

    /**
     * [OracleHikariCPTest.queryEmpTable] - empno=7839, ename=KING
     * [OracleHikariCPTest.queryEmpTable] - empno=7698, ename=BLAKE
     * [OracleHikariCPTest.queryEmpTable] - empno=7782, ename=CLARK
     * [OracleHikariCPTest.queryEmpTable] - empno=7566, ename=JONES
     * [OracleHikariCPTest.queryEmpTable] - empno=7654, ename=MARTIN
     * [OracleHikariCPTest.queryEmpTable] - empno=7499, ename=ALLEN
     * [OracleHikariCPTest.queryEmpTable] - empno=7844, ename=TURNER
     * [OracleHikariCPTest.queryEmpTable] - empno=7900, ename=JAMES
     * [OracleHikariCPTest.queryEmpTable] - empno=7521, ename=WARD
     * [OracleHikariCPTest.queryEmpTable] - empno=7902, ename=FORD
     * [OracleHikariCPTest.queryEmpTable] - empno=7369, ename=SMITH
     * [OracleHikariCPTest.queryEmpTable] - empno=7788, ename=SCOTT
     * [OracleHikariCPTest.queryEmpTable] - empno=7876, ename=ADAMS
     * [OracleHikariCPTest.queryEmpTable] - empno=7934, ename=MILLER
     */
    @Test
    void queryEmpTable() throws SQLException {
        String query = "select * from emp";
        Connection connection = OracleDataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query); // Change this to your table name

        while (resultSet.next()) {
            log.info("empno={}, ename={}", resultSet.getString("empno"), resultSet.getString("ename"));
        }

        connection.close();
        statement.close();
        resultSet.close();
    }

}
