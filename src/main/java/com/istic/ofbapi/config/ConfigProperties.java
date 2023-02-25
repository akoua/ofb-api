package com.istic.ofbapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "custom.path.firebase")
public class ConfigProperties {
    private Resource serviceAccount;
    public Resource getServiceAccount(){
        return serviceAccount;
    }
    public void setServiceAccount(Resource serviceAccount) {
        this.serviceAccount = serviceAccount;
    }
}
