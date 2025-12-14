package com.restaurant.saas.infrastructure.multitenant;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(TenantContext.class);

    public static void setCurrentTenant(String tenantId) {
        if (tenantId == null || tenantId.trim().isEmpty()){
            log.warn("Tentativa de definir tenant nulo ou vazio");
            return;
        }

        log.debug("Definindo tnant para thread {}: {}",
                Thread.currentThread().getName(), tenantId);

        CURRENT_TENANT.set(tenantId);
    }

    public static String getCurrentTenant() {

        String tenant = CURRENT_TENANT.get();

        log.debug("Obtendo tenant da thread {}: {}",
                Thread.currentThread().getName(), tenant);

        return tenant;
    }

}
