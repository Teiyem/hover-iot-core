package com.hover.iot.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.model.Device;

/**
 * A Data transfer object representing a simplified version of a {@link Device} entity.
 *
 * @param id       The id of the device.
 * @param name     The name of the device.
 * @param firmware The firmware version of the device.
 * @param status   The status of the device.
 * @param type     The type of the device.
 * @param platform The platform of the device.
 */
@JsonTypeName("device")
public record DeviceDto(Long id, String name, String firmware, boolean status, String type, String platform) {
}


