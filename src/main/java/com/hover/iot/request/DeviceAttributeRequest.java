package com.hover.iot.request;

import com.hover.iot.entity.Attribute;
import com.hover.iot.entity.Device;

import java.util.List;

/**
 * A representation of a request object to perform an action on a {@link Device}.
 *
 * @param attributes The attribute to of the action.
 */
public record DeviceAttributeRequest(List<Attribute> attributes) { }

