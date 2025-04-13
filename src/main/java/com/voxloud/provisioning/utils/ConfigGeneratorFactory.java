package com.voxloud.provisioning.utils;

import com.voxloud.provisioning.utils.impl.ConferenceConfigGenerator;
import com.voxloud.provisioning.utils.impl.DeskConfigGenerator;

public class ConfigGeneratorFactory {
    public static ConfigGenerator getGenerator(String model) {
        switch (model.toUpperCase()) {
            case "DESK":
                return new DeskConfigGenerator();
            case "CONFERENCE":
                return new ConferenceConfigGenerator();
            default:
                throw new IllegalArgumentException("Unsupported device model: " + model);
        }
    }
}
