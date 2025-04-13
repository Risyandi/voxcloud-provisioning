package com.voxloud.provisioning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class Device {

    @Id
    @Column(name = "MAC_ADDRESS")
    private String macAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType model;

    @Lob
    @Column(name = "OVERRIDE_FRAGMENT")
    private String overrideFragment;

    private String username;
    private String password;

    public enum DeviceType {
        CONFERENCE,
        DESK
    }
}