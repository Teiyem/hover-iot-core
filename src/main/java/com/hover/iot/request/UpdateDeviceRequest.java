package com.hover.iot.request;

import com.hover.iot.model.Attribute;
import com.hover.iot.model.Credentials;
import com.hover.iot.model.Device;

import java.util.List;

/**
 * A representation of a request for updating an existing {@link Device}.
 *
 * @param name        The new name of the device.
 * @param host        The new ip or url address of the device.
 * @param firmware    The new firmware version of the device.
 * @param room        The new room that the device belongs to.
 * @param credentials The new credentials used to connect to the device.
 */
public record UpdateDeviceRequest(String name, String host, String firmware, String room, Credentials credentials) {
}
