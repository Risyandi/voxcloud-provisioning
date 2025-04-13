package com.voxloud.provisioning.service;

import java.util.List;

import com.voxloud.provisioning.entity.Device;

public interface ProvisioningService {
    List<Device> getAllDevices();

    List<Device> getDevicesById(String mac);

    String generateConfiguration(String macAddress);
}