package com.restaurant.saas.application.services;

import com.restaurant.saas.domain.entities.Tenant;
import com.restaurant.saas.infrastructure.multitenant.TenantContext;
import com.restaurant.saas.infrastructure.persistence.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TenantService {

    private final TenantRepository tenantRepository;

    public List<Tenant> findAll() {
        log.debug("Buscando todos os tenants");
        List<Tenant> tenants = tenantRepository.findAll();
        log.info("Encontrados {} tenants", tenants.size());
        return tenants;
    }

    public Tenant getCurrentTenant() {
        String schemaName = TenantContext.getCurrentTenant();

        if (schemaName == null || schemaName.trim().isEmpty()) {
            log.error("Tentativa de obter tenant atual sem contexto definido");
            throw new IllegalStateException("Nenhum tenant definido no contexto");
        }

        log.debug("Buscando tenant com schema {}", schemaName);

        return tenantRepository.findBySchemaName(schemaName)
                .orElseThrow(() -> {
                    log.error("Tenant não encontrado para schema: {}", schemaName);
                    return new RuntimeException("Tenant não encontrado: " + schemaName);
                });
    }

    public Tenant findBySubdomain(String subdomain) {
        log.debug("Buscando tenant por subdomínio: {}", subdomain);

        return tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> {
                    log.warn("Tenant não encontrado para subdomínio: {}", subdomain);
                    return new RuntimeException("Tenant não encontrado: " + subdomain);
                });
    }

}
