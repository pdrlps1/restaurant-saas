package com.restaurant.saas.infrastructure.multitenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrentTenantIdentifierResolver
                implements org.hibernate.context.spi.CurrentTenantIdentifierResolver{

    private static final String DEFAULT_TENANT = "public";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = TenantContext.getCurrentTenant();

        if (tenant == null || tenant.trim().isEmpty()) {
            log.trace("Nenhum tenant definido, usando schema padrão: {}", DEFAULT_TENANT);
            return DEFAULT_TENANT;
        }

        log.trace("Resolvendo tenant identifier: {}", tenant);

        return tenant;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }

}
