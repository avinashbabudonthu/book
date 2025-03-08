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

    /**
     * mysql-tasksdb-connection-pool - configuration:
     * allowPoolSuspension................................false
     * autoCommit................................true
     * catalog................................none
     * connectionInitSql................................"select curdate()"
     * connectionTestQuery................................none
     * connectionTimeout................................5000
     * dataSource................................none
     * dataSourceClassName................................none
     * dataSourceJNDI................................none
     * dataSourceProperties................................{password=<masked>, prepStmtCacheSqlLimit=2048, cachePrepStmts=true, prepStmtCacheSize=250}
     * driverClassName................................none
     * exceptionOverrideClassName................................none
     * healthCheckProperties................................{}
     * healthCheckRegistry................................none
     * idleTimeout................................600000
     * initializationFailTimeout................................1
     * isolateInternalQueries................................false
     * jdbcUrl................................jdbc:mysql://localhost:3306/tasksdb?useSSL=false&verifyServerCertificate=false&noAccessToProcedureBodies=true
     * keepaliveTime................................0
     * leakDetectionThreshold................................0
     * maxLifetime................................1800000
     * maximumPoolSize................................20
     * metricRegistry................................none
     * metricsTrackerFactory................................none
     * minimumIdle................................20
     * password................................<masked>
     * poolName................................"mysql-tasksdb-connection-pool"
     * readOnly................................false
     * registerMbeans................................false
     * scheduledExecutor................................none
     * schema................................none
     * threadFactory................................internal
     * transactionIsolation................................default
     * username................................"tasksadmin"
     * validationTimeout................................5000
     * [HikariDataSource.<init>] - mysql-connection-pool - Starting...
     * [DriverDataSource.<init>] - Loaded driver with class name com.mysql.cj.jdbc.Driver for jdbcUrl=jdbc:mysql://localhost:3306/practice?useSSL=false&verifyServerCertificate=false&noAccessToProcedureBodies=true
     * [HikariPool.checkFailFast] - mysql-connection-pool - Added connection com.mysql.cj.jdbc.ConnectionImpl@51549490
     * [HikariDataSource.<init>] - mysql-connection-pool - Start completed.
     * [MySQLHikariCPTest.getConnection] - connection1=HikariProxyConnection@1094674892 wrapping com.mysql.cj.jdbc.ConnectionImpl@51549490
     * [HikariPool$PoolEntryCreator.call] - mysql-connection-pool - Added connection com.mysql.cj.jdbc.ConnectionImpl@fcab2b6
     * [MySQLHikariCPTest.getConnection] - connection2=HikariProxyConnection@1003693033 wrapping com.mysql.cj.jdbc.ConnectionImpl@fcab2b6
     * [HikariPool$PoolEntryCreator.call] - mysql-connection-pool - Added connection com.mysql.cj.jdbc.ConnectionImpl@4596358f
     * [MySQLHikariCPTest.getConnection] - connection3=HikariProxyConnection@209429254 wrapping com.mysql.cj.jdbc.ConnectionImpl@4596358f
     * [HikariPool$PoolEntryCreator.call] - mysql-connection-pool - Added connection com.mysql.cj.jdbc.ConnectionImpl@7a37a0e9
     * [MySQLHikariCPTest.getConnection] - connection4=HikariProxyConnection@1830190936 wrapping com.mysql.cj.jdbc.ConnectionImpl@7a37a0e9
     */
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

    /**
     * mysql-tasksdb-connection-pool - configuration:
     * allowPoolSuspension................................false
     * autoCommit................................true
     * catalog................................none
     * connectionInitSql................................"select curdate()"
     * connectionTestQuery................................none
     * connectionTimeout................................5000
     * dataSource................................none
     * dataSourceClassName................................none
     * dataSourceJNDI................................none
     * dataSourceProperties................................{password=<masked>, prepStmtCacheSqlLimit=2048, cachePrepStmts=true, prepStmtCacheSize=250}
     * driverClassName................................none
     * exceptionOverrideClassName................................none
     * healthCheckProperties................................{}
     * healthCheckRegistry................................none
     * idleTimeout................................600000
     * initializationFailTimeout................................1
     * isolateInternalQueries................................false
     * jdbcUrl................................jdbc:mysql://localhost:3306/tasksdb?useSSL=false&verifyServerCertificate=false&noAccessToProcedureBodies=true
     * keepaliveTime................................0
     * leakDetectionThreshold................................0
     * maxLifetime................................1800000
     * maximumPoolSize................................20
     * metricRegistry................................none
     * metricsTrackerFactory................................none
     * minimumIdle................................20
     * password................................<masked>
     * poolName................................"mysql-tasksdb-connection-pool"
     * readOnly................................false
     * registerMbeans................................false
     * scheduledExecutor................................none
     * schema................................none
     * threadFactory................................internal
     * transactionIsolation................................default
     * username................................"tasksadmin"
     * validationTimeout................................5000
     * [HikariDataSource.<init>] - mysql-connection-pool - Starting...
     * [DriverDataSource.<init>] - Loaded driver with class name com.mysql.cj.jdbc.Driver for jdbcUrl=jdbc:mysql://localhost:3306/practice?useSSL=false&verifyServerCertificate=false&noAccessToProcedureBodies=true
     * [HikariPool.checkFailFast] - mysql-connection-pool - Added connection com.mysql.cj.jdbc.ConnectionImpl@3f57bcad
     * [HikariDataSource.<init>] - mysql-connection-pool - Start completed.
     * date=2023-04-18
     */
    @DisplayName("Execute query")
    @Test
    void executeQuery(){
        String sqlQuery = "select current_date() as date";
        try (Connection connection = PostgreSQLDataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlQuery);
             ResultSet resultset = ps.executeQuery()) {
            while (resultset.next()){
                String date = resultset.getString("date");
                log.info("date={}", date);
            }
        } catch (SQLException e) {
            log.error("Exception", e);
        }
    }

}
