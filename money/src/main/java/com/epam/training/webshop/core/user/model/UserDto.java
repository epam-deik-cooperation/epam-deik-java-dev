package com.epam.training.webshop.core.user.model;

import com.epam.training.webshop.core.user.persistence.entity.User;
import java.util.Objects;

public class UserDto {

    private final String username;
    private final User.Role role;

    public UserDto(String username, User.Role role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(username, userDto.username) && role == userDto.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role);
    }

    @Override
    public String toString() {
        return "UserDto{" +
            "username='" + username + '\'' +
            ", role=" + role +
            '}';
    }
}
