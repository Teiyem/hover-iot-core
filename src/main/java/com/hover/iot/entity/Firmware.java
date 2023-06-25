package com.hover.iot.entity;

import com.hover.iot.enumeration.DeviceType;
import jakarta.persistence.*;

import java.sql.Blob;

/**
 * An entity class that represents a device's firmware.
 */
@Entity
@Table(name = "TBL_FIRMWARE")
public class Firmware {

    /**
     * The firmware's id.
     */
    @Id
    @SequenceGenerator(
            name = "TBL_FIRMWARE_ID_SEQ",
            sequenceName = "TBL_FIRMWARE_ID_SEQ",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "TBL_FIRMWARE_ID_SEQ"
    )
    private Long id;

    /**
     * The firmware's file name.
     */
    private String filename;

    /**
    /**
     * The firmware's version.
     */
    private String version;

    /**
     * The firmware's device type.
     */
    private DeviceType type;

    /**
     * The firmware's platform.
     */
    private String platform;

    /**
     * The firmware's file.
     */
    @Lob
    private byte[] file;

    /**
     * Initializes a new instance of {@link Firmware} class. Default Constructor.
     */
    public Firmware() {
    }

    /**
     * Gets the firmware's id.
     *
     * @return The firmware's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the firmware's id.
     *
     * @param id The firmware's id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the firmware's file name.
     *
     * @return The firmware's file name.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the firmware's file name.
     *
     * @param filename The firmware's file name to set.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Gets the firmware's platform.
     *
     * @return The firmware's platform.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the firmware's version.
     *
     * @param version The firmware's version to set.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the firmware's device type.
     *
     * @return The firmware's device type.
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Sets the firmware's device type.
     *
     * @param type The firmware's device type to set.
     */
    public void setType(DeviceType type) {
        this.type = type;
    }

    /**
     * Gets the firmware's platform.
     *
     * @return The firmware's platform.
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Sets the firmware's platform.
     *
     * @param platform The firmware's platform to set.
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * Gets the firmware's file.
     *
     * @return The firmware's file.
     */
    public byte[] getFile() {
        return file;
    }

    /**
     * Sets the firmware's file.
     *
     * @param file The firmware's file to set.
     */
    public void setFile(byte[] file) {
        this.file = file;
    }
}
