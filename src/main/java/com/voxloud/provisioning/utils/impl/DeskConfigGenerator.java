package com.voxloud.provisioning.utils.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.utils.ConfigGenerator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DeskConfigGenerator implements ConfigGenerator {
    /**
     * Generates a configuration for a desk device given its details and provisioning properties.
     * 
     * @param device the desk device to generate the configuration for
     * @param properties the provisioning properties to use for default values
     * @return the JSON configuration for the device
     * @throws RuntimeException if there is an error parsing the override fragment or converting the config to JSON
     */
    @Override
    public String generateConfig(Device device, ProvisioningProperties properties) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode config = mapper.createObjectNode();

        // Load default properties
        config.put("username", device.getUsername());
        config.put("password", device.getPassword());
        config.put("domain", properties.getDomain());
        config.put("port", properties.getPort());
        config.put("codecs", String.join(",", properties.getCodecs()));

        // Apply override fragment if present
        if (device.getOverrideFragment() != null && !device.getOverrideFragment().isEmpty()) {
            try {
                Properties overrideProps = new Properties();
                overrideProps.load(new StringReader(device.getOverrideFragment()));
                overrideProps.forEach((key, value) -> config.put((String) key, (String) value));
            } catch (IOException e) {
                throw new RuntimeException("Failed to parse override fragment", e);
            }
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(config);
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert config to JSON", e);
        }
    }
}
