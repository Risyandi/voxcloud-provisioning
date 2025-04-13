package com.voxloud.provisioning.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "provisioning")
@Data
@Getter
@Setter
public class ProvisioningProperties {
    private String domain;
    private Integer port;
    private List<String> codecs;
}

