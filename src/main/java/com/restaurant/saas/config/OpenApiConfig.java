package com.restaurant.saas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant SaaS API")
                        .version("1.0.0")
                        .description("""
                                                                API REST para sistema SaaS de gestão de restaurantes.
                                
                                                                ## 🏗️ Arquitetura Multi-tenant
                                
                                                                Este sistema utiliza estratégia **Schema per Tenant**.
                                                                Cada restaurante possui seu próprio schema no PostgreSQL.
                                
                                                                ## 🔐 Autenticação Multi-tenant
                                
                                                                A maioria dos endpoints requer o header **X-Tenant-ID**.
                                
                                                                Exemplo:
                                ```
                                                                X-Tenant-ID: tenant_001
                                ```
                                
                                                                **Tenants disponíveis para teste:**
                                                                - `tenant_001` - Restaurante Bella Vista
                                                                - `tenant_002` - Bar do João
                                
                                                                ## 📚 Estrutura
                                
                                                                - **Domain:** Entidades de negócio
                                                                - **Application:** Serviços e casos de uso
                                                                - **Infrastructure:** Repositories e integrações
                                                                - **Presentation:** Controllers REST
                                """)
                        .contact(new Contact()
                                .name("Pedro")
                                .email("pdrlps.dev@gmail.com")
                                .url("https://www.linkedin.com/in/pedro-otavio-lopes/"))
                        .license(new License()
                                .name("Projeto de Estudo")
                                .url("https://github.com/pdrlps1/restaurant-saas")));
    }

    @Bean
    public OperationCustomizer customizeOperations() {
        return (operation, handlerMethod) -> {
            // Adicionar parâmetro X-Tenant-ID globalmente
            // (exceto para endpoints que não precisam)
            String path = handlerMethod.toString();

            // Se não for o endpoint de listagem (que não precisa de tenant)
            if (!path.contains("listAll")) {
                operation.addParametersItem(
                        new Parameter()
                                .in("header")
                                .name("X-Tenant-ID")
                                .description("""
                                        Identificador do tenant (schema do restaurante).
                                        
                                        **Valores disponíveis:**
                                        - `tenant_001` - Restaurante Bella Vista
                                        - `tenant_002` - Bar do João
                                        """)
                                .required(true)
                                .schema(new StringSchema()
                                        ._default("tenant_001")
                                        .example("tenant_001"))
                );
            }

            return operation;
        };
    }
}
