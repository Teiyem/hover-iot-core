package com.hover.iot.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.model.Attribute;
import com.hover.iot.model.Device;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A representation of a request object to perform an action on a {@link Device}.
 *
 * @param attributes The attribute to of the action.
 */
public record DeviceAttributeRequest(List<Attribute> attributes) { }

