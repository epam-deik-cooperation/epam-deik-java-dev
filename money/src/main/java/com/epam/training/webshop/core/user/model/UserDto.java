package com.epam.training.webshop.core.user.model;

import com.epam.training.webshop.core.user.persistence.User;

public record UserDto(String username, User.Role role) {
}
