package com.restaurant.saas.infrastructure.multitenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class TenantInterceptor implements HandlerInterceptor {
    private static final String TENANT_HEADER = "X-Tenant-ID";
    private static final Logger log = LoggerFactory.getLogger(TenantInterceptor.class);

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
            ) {
        String tenantId = request.getHeader(TENANT_HEADER);

        if (tenantId == null || tenantId.trim().isEmpty()) {
            log.warn("Requisição sem tenant ID - URI: {} | IP: {}", request.getRequestURI(), request.getRemoteAddr());

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            return false;
        }

        log.info("Requisição recebida - Tenant: {} | URI: {} | Método: {} | IP: {} ",
                tenantId,
                request.getRequestURI(),
                request.getMethod(),
                request.getRemoteAddr());

        TenantContext.setCurrentTenant(tenantId);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull Object handler,
        Exception ex) {

        String tenantId = TenantContext.getCurrentTenant();

        if(ex == null) {
            log.debug("Requisição concluída com sucesso - Tenant: {} | URI: {} | Status: {}",
                    tenantId,
                    request.getRequestURI(),
                    response.getStatus());
        } else {
            log.error("Requisição concluída com erro - Tenant: {} | URI: {} | Erro: {}",
                    tenantId,
                    request.getRequestURI(),
                    ex.getMessage());
        }

        TenantContext.clear();
    }
}
