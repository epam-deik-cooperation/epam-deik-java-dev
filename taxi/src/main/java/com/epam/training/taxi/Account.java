package com.epam.training.taxi;

import java.util.Objects;

public class Account {

    private final Long ID;
    private final Double discount;
    private final String name;

    public Account(Long ID, String name, Double discount) {
        this.ID = ID;
        this.discount = discount;
        this.name = name;
    }
    public Long getID() {
        return ID;
    }

    public Double getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(ID, account.ID) && Objects.equals(discount, account.discount) && Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, discount, name);
    }
}
