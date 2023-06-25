package com.hover.iot.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;
import com.hover.iot.enumeration.DeviceStatus;
import com.hover.iot.enumeration.DeviceType;

import java.util.List;

/**
 * A Data transfer object representing a simplified version of a {@link Device} entity.
 *
 * @param id         The device's id.
 * @param name       The device's name.
 * @param attributes The device's attributes.
 * @param firmware   The device's firmware.
 * @param status     The device's name.
 * @param room       The device's room.
 * @param type       The device's type.
 * @param platform   The device's platform.
 */
@JsonTypeName("device")
public record DeviceDTO(Long id, String name, List<Attribute> attributes, String firmware, DeviceStatus status, RoomDTO room,
                        DeviceType type, String platform) {
}


