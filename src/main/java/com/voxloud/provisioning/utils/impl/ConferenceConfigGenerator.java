package com.voxloud.provisioning.utils.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.utils.ConfigGenerator;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class ConferenceConfigGenerator implements ConfigGenerator {
    /**
     * Generates a configuration for a conference device. The configuration includes the device's
     * username, password, domain, port and codecs. If an override fragment is present, it is applied
     * to the configuration. The configuration is returned as a JSON string.
     *
     * @param device the device to generate the configuration for
     * @param properties the provisioning properties
     * @return the configuration as a JSON string
     */
    @Override
    public String generateConfig(Device device, ProvisioningProperties properties) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode config = mapper.createObjectNode();

        // Load default properties
        config.put("username", device.getUsername());
        config.put("password", device.getPassword());
        config.put("domain", properties.getDomain());
        config.put("port", String.valueOf(properties.getPort()));
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