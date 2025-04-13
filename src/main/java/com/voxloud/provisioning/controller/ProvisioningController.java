package com.voxloud.provisioning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.voxloud.provisioning.service.ProvisioningService;
import com.voxloud.provisioning.entity.Device;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provisioning")
public class ProvisioningController {
    private final ProvisioningService provisioningService;

    public ProvisioningController(ProvisioningService provisioningService) {
        this.provisioningService = provisioningService;
    }

    /**
     * Greeting endpoint for verifying that the service is up and running.
     * @return a friendly message
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hi, Greetings from Spring Boot.";
    }

    /**
     * Returns a configuration string for the given device based on its provisioning information.
     * The MAC address is case-insensitive.
     * @param macAddress the MAC address of the device to provision
     * @return a ResponseEntity with the configuration string, or an error message if the device is not found
     *  or if there was an unexpected error generating the configuration.
     */
    @GetMapping("/{macAddress}")
    public ResponseEntity<String> getProvisioning(@PathVariable String macAddress) {
        try {
            String config = provisioningService.generateConfiguration(macAddress.toLowerCase());
            return ResponseEntity.ok(config);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\":\"Device not found\",\"macAddress\":\"" + macAddress + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating config: " + e.getMessage());
        }
    }

    /**
     * Returns a list of all devices in the provisioning database.
     * @return a list of Device objects
     */
    @GetMapping("/devices")
    public List<Device> getDevices() {
        return provisioningService.getAllDevices();
    }

    /**
     * Returns a list of all devices in the provisioning database which match the given MAC address.
     * The MAC address is case-insensitive.
     * @param mac the MAC address of the devices to retrieve
     * @return a list of Device objects
     */
    @GetMapping("/devices/{mac}")
    public List<Device> getDevice(@PathVariable String mac) {
        return provisioningService.getDevicesById(mac);
    }

}