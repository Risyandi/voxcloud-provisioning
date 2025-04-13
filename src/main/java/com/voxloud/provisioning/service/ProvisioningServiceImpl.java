package com.voxloud.provisioning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.voxloud.provisioning.config.ProvisioningProperties;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.utils.ConfigGenerator;
import com.voxloud.provisioning.utils.ConfigGeneratorFactory;

@Service
public class ProvisioningServiceImpl implements ProvisioningService {
    private final DeviceRepository deviceRepository;
    private final ProvisioningProperties provisioningProperties;

    public ProvisioningServiceImpl(DeviceRepository deviceRepository, ProvisioningProperties provisioningProperties) {
        this.deviceRepository = deviceRepository;
        this.provisioningProperties = provisioningProperties;
    }

    /**
     * Retrieves all devices stored in the database.
     *
     * @return The list of all devices.
     */
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    /**
     * Retrieves a list of devices with the given MAC address. If no such device exists, an empty list is returned.
     *
     * @param mac the MAC address of the device to retrieve
     * @return a list of devices with the given MAC address, or an empty list if no such device exists
     */
    public List<Device> getDevicesById(String mac) {
        return deviceRepository.findById(mac)
                .map(List::of)
                .orElseGet(List::of);
    }

    /**
     * Generates a configuration for a device identified by its MAC address.
     * If the device is not found, an IllegalArgumentException is thrown.
     * 
     * @param macAddress the MAC address of the device for which to generate the configuration
     * @return a string representation of the generated configuration
     * @throws IllegalArgumentException if the device is not found
     */

    @Override
    public String generateConfiguration(String macAddress) {
        Optional<Device> deviceOpt = deviceRepository.findById(macAddress);
        if (deviceOpt.isEmpty()) {
            throw new IllegalArgumentException("Device not found");
        }

        Device device = deviceOpt.get();
        ConfigGenerator generator = ConfigGeneratorFactory.getGenerator(device.getModel().name());
        return generator.generateConfig(device, provisioningProperties);
    }

}
