package com.hover.iot.platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hover.iot.exception.PlatformApiException;
import com.hover.iot.model.Attribute;
import com.hover.iot.model.Device;

/**
 * The PlatformApi interface defines the contract for interacting with a specific platform.
 * Implementations of this interface provide the necessary methods to control devices and perform platform-specific operations.
 */
public interface PlatformApi {

    /**
     * Gets the name o the platform.
     *
     * @return The name of the platform.
     */
    String getName();

    /**
     * Sets attribute of a device.
     *
     * @param device  The device to set the attribute value of.
     * @param attribute The attribute to set.
     * @throws PlatformApiException If an error occurs while setting the attribute.
     * @throws JsonProcessingException If an error occurred while deserializing or serializing.
     */
        void setAttribute(Device device, Attribute attribute) throws Exception;

    /**
     * Gets the attribute of a device.
     *
     * @param device  The device to get the attribute value of.
     * @throws PlatformApiException If an error occurs while getting the attribute.
     * @throws JsonProcessingException If an error occurred while deserializing or serializing.
     */
    Attribute getAttribute(Device device) throws Exception;

    /**
     * Checks if the device is reachable and can be controlled through the platform.
     *
     * @param device The device to check.
     * @return true if the device is reachable, false otherwise.
     */
    boolean isDeviceReachable(Device device);
}
