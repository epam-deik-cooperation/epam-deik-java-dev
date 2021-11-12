package com.epam.training.webshop.model;

import java.util.Objects;
import java.util.StringJoiner;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coupon {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double value;

    protected Coupon() {

    }

    public Coupon(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Double.compare(coupon.value, value) == 0 &&
                Objects.equals(name, coupon.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Coupon.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("value=" + value)
                .toString();
    }
}
