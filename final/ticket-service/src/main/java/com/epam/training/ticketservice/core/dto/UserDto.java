package com.epam.training.ticketservice.core.dto;

import com.epam.training.ticketservice.core.model.User;

public record UserDto(String userName, User.Role role) {
}
