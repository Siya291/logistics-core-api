package com.project.smartcitylogistics.config;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class SchemaMultiTenantConnectionProvider implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    public final String DEFAULT_SCHEMA = "vendor_delivery_co";

    public SchemaMultiTenantConnectionProvider(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = dataSource.getConnection();
        try (java.sql.Statement statement = connection.createStatement()) {
            // This ensures both the tenant data AND the public PostGIS functions are visible
            statement.execute("SET search_path TO " + tenantIdentifier + ", public");
        } catch (SQLException e) {
            connection.close(); // Safety first: close if the path switch fails
            throw e;
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try (java.sql.Statement statement = connection.createStatement()) {
            statement.execute("SET search_path TO " + DEFAULT_SCHEMA + ", public");
        } catch (SQLException e) {
            // Just log it or ignore, the connection is closing anyway
        }
        connection.close();
    }

    // Standard Hibernate overrides...
    @Override public boolean supportsAggressiveRelease() { return false; }
    @Override public boolean isUnwrappableAs(Class<?> unwrapType) { return false; }
    @Override public <T> T unwrap(Class<T> unwrapType) { return null; }
    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }
}