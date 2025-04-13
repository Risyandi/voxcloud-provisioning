package com.voxloud.provisioning.utils;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.config.ProvisioningProperties;

public interface ConfigGenerator {
    String generateConfig(Device device, ProvisioningProperties properties);
}
