package com.restaurant.saas.presentation.controllers;

import com.restaurant.saas.application.services.TenantService;
import com.restaurant.saas.domain.entities.Tenant;
import com.restaurant.saas.infrastructure.multitenant.TenantContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tenants", description = "Gerenciamento de Tenants (restaurantes")
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    @Operation(
            summary = "Listar todos os tenants",
            description = """
                    Lista todos os tenants cadastrados no sistema
                    
                    **Importante:** Esse endpoint acessa o schema PUBLIC. Não requer o header X-Tenant-ID.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.")
    })
    public ResponseEntity<List<Tenant>> listAll() {
        log.info("Listando todos os tenants.");
        List<Tenant> tenants = tenantService.findAll();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/current")
    @Operation(
            summary = "Obter tenant atual",
            description = """
            Retorna informações detalhadas do tenant atual.
            
            **REQUER:** Header X-Tenant-ID
            
            Demonstra o isolamento multi-tenant funcionando.
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tenant encontrado"),
            @ApiResponse(responseCode = "400", description = "Header X-Tenant-ID não informado"),
            @ApiResponse(responseCode = "404", description = "Tenant não encontrado")
    })
    public ResponseEntity<Map<String, Object>> getCurrentTenant() {
        String tenantId = TenantContext.getCurrentTenant();
        log.info("Buscando informações do tenant: {}", tenantId);

        Tenant tenant = tenantService.getCurrentTenant();

        Map<String, Object> response = new HashMap<>();
        response.put("tenantId", tenantId);
        response.put("companyName", tenant.getCompanyName());
        response.put("subdomain", tenant.getSubdomain());
        response.put("isActive", tenant.isActive());
        response.put("threadName", Thread.currentThread().getName());
        response.put("message", "Multi-tenancy funcionando! 🎉");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    @Operation(
            summary = "Endpoint de teste",
            description = """
            Endpoint para testar o contexto multi-tenant.
            
            Retorna informações técnicas úteis para debug.
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Teste executado com sucesso")
    })
    public ResponseEntity<Map<String, Object>> test() {
        String tenantId = TenantContext.getCurrentTenant();

        Map<String, Object> response = new HashMap<>();
        response.put("currentTenant", tenantId);
        response.put("threadName", Thread.currentThread().getName());
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", "Multi-tenant context working!");

        return ResponseEntity.ok(response);
    }
}
