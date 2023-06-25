package com.hover.iot.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.hover.iot.entity.User;

/**
 * A Data transfer object representing a simplified version of a {@link User} entity.
 *
 * @param id   The id of the user.
 * @param name The name of the user.
 * @param username username of the user.
 */
@JsonTypeName("user")
public record UserDTO(Integer id, String name, String username) { }
