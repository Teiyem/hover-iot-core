package com.hover.iot.request;

import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;
import com.hover.iot.enumeration.DeviceType;
import com.hover.iot.model.Credentials;

import java.util.List;


/**
 * Represents a request for adding a new {@link Device}.
 *
 * @param name        The name of the device.
 * @param host        The host address of the device.
 * @param attributes  The attributes of the device.
 * @param firmware    The firmware of the device.
 * @param room        The room of the device.
 * @param type        The type of the device.
 * @param credentials The credentials of the device.
 * @param platform    The platform of the device.
 */
public record AddDeviceRequest(String name, String host, List<Attribute> attributes, String firmware, String room,
                               DeviceType type, Credentials credentials, String platform) {
}
