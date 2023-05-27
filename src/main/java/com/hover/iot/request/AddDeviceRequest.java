package com.hover.iot.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.model.Attribute;
import com.hover.iot.model.Credentials;
import com.hover.iot.model.Device;
import com.hover.iot.model.Room;

import java.util.List;

/**
 * A representation of a request object for adding a new {@link Device}.
 *
 * @param name        The name of the device.
 * @param host        The IP address of the device.
 * @param attributes  The attributes of the device.
 * @param firmware    The firmware version of the device.
 * @param room        The room that the device belongs to.
 * @param type        The type of the device.
 * @param credentials The credentials used to connect to the device.
 * @param platform    The platform of the device.
 */
public record AddDeviceRequest(String name, String host, List<Attribute> attributes, String firmware, String room,
                               String type, Credentials credentials, String platform) {
}
