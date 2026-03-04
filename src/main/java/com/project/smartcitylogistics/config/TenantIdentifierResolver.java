package com.project.smartcitylogistics.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenantId();
        return (tenantId != null) ? tenantId : "vendor_delivery_co"; //fall back
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}