package com.epam.training.webshop.ui.command.impl;

import com.epam.training.webshop.ui.command.Command;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractCommand implements Command {

    private final String userType;
    private final String entityType;
    private final String action;

    public AbstractCommand(String userType, String entityType, String action) {
        this.userType = userType;
        this.entityType = entityType;
        this.action = action;
    }

    @Override
    public String process(String commandString) {
        String[] commandParts = commandString.split(" ");
        return process(Arrays.copyOfRange(commandParts, 3, commandParts.length));
    }

    public abstract String process(String[] params);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof AbstractCommand)) {
            return false;
        }
        AbstractCommand other = (AbstractCommand) obj;
        if (!action.equals(other.action)) {
            return false;
        } else if (!entityType.equals(other.entityType)) {
            return false;
        } else {
            return userType.equals(other.userType);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(userType, entityType, action);
    }
}
