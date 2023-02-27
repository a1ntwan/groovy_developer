package ru.otus.hw.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException
import java.util.logging.Logger

class DriverManagerDataSource implements DataSource {
    private DataSource dataSourcePool;

    DriverManagerDataSource(String url, String user, String pwd) {
        createConnectionPool(url, user, pwd);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSourcePool.getConnection();
    }

    @Override
    Connection getConnection(String username, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    PrintWriter getLogWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException();

    }

    @Override
    int getLoginTimeout() {
        throw new UnsupportedOperationException();
    }

    @Override
    void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException();
    }

    @Override
    Logger getParentLogger() {
        throw new UnsupportedOperationException();
    }

    @Override
    <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException();
    }

    @Override
    boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException();
    }

    private void createConnectionPool(String url, String user, String pwd) {
        Properties properties = new Properties()
        File propertiesFile = new File('application.properties')
        propertiesFile.withInputStream {
            properties.load(it)
        }

        def config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setConnectionTimeout(properties.getProperty('ConnectionTimeout').toInteger()); //ms
        config.setIdleTimeout(properties.getProperty('IdleTimeout').toInteger()); //ms
        config.setMaxLifetime(properties.getProperty('MaxLifetime').toInteger());//ms
        config.setAutoCommit(properties.getProperty('AutoCommit').toBoolean());
        config.setMinimumIdle(properties.getProperty('MinimumIdle').toInteger());
        config.setMaximumPoolSize(properties.getProperty('MaximumPoolSize').toInteger());
        config.setPoolName(properties.getProperty('PoolName'));
        config.setRegisterMbeans(properties.getProperty('RegisterMbeans').toBoolean());

        config.addDataSourceProperty("cachePrepStmts", properties.getProperty('cachePrepStmts').toBoolean());
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty('prepStmtCacheSize').toInteger());
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty('prepStmtCacheSqlLimit').toInteger());

        config.setUsername(user);
        config.setPassword(pwd);

        dataSourcePool = new HikariDataSource(config);
    }
}
