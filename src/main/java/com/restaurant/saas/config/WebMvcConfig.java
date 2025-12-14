package com.restaurant.saas.config;

import com.restaurant.saas.infrastructure.multitenant.TenantInterceptor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry){
        registry.addInterceptor(tenantInterceptor).addPathPatterns("/api/**").excludePathPatterns("/api/auth/**");
    }
}
