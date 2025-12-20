package com.restaurant.saas.infrastructure.multitenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class SchemaMultiTenantConnectionProvider
                implements MultiTenantConnectionProvider<String> {

    private final DataSource dataSource;

    public Connection getAnyConection() throws SQLException {
        log.trace("Obtendo conexão sem tenant específico...");
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        if (connection != null) {
            log.trace("Liberando conexão não específica do tenant...");
            connection.close();
        }
    }

    @Override
    public Connection getConnection(String tenantId) throws SQLException {
        log.debug("Obtendo conexão para tenant: {}", tenantId);

        Connection connection = dataSource.getConnection();

        try {
            connection.setSchema(tenantId);
            log.trace("Schema configurado para: {}", tenantId);
            return connection;
        } catch (SQLException e) {
            log.error("Erro ao configurar schema {} na conexão: {}", tenantId, e.getMessage());
            throw e;
        }
    }

    @Override
    public void releaseConnection(String tenantId, Connection connection) throws SQLException {
        if (connection != null) {
            log.debug("Liberando conexão do tenant: {}", tenantId);

            try {
                connection.setSchema("public");
                log.trace("Schema resetado para 'public'");
            } catch (SQLException e) {
                log.warn("Erro ao resetar schema para 'public': {}", e.getMessage());
            } finally {
                connection.close();
            }
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class<?> unwrapType) {
            return MultiTenantConnectionProvider.class.isAssignableFrom(unwrapType)
                    || DataSource.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if (isUnwrappableAs(unwrapType)) {
            if (DataSource.class.isAssignableFrom(unwrapType)) {
                return unwrapType.cast(dataSource);
            }
            return unwrapType.cast(this);
        }
        throw new IllegalArgumentException(
                "Não é possível desembrulhar como " + unwrapType.getName()
        );
    }
}
