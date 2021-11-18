package com.epam.training.webshop.core.product.persistence.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private double netPriceAmount;
    private String netPriceCurrencyCode;

    public Product() {
    }

    public Product(String name, double netPriceAmount, String netPriceCurrencyCode) {
        this.name = name;
        this.netPriceAmount = netPriceAmount;
        this.netPriceCurrencyCode = netPriceCurrencyCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNetPriceAmount() {
        return netPriceAmount;
    }

    public void setNetPriceAmount(double netPriceAmount) {
        this.netPriceAmount = netPriceAmount;
    }

    public String getNetPriceCurrencyCode() {
        return netPriceCurrencyCode;
    }

    public void setNetPriceCurrencyCode(String netPriceCurrencyCode) {
        this.netPriceCurrencyCode = netPriceCurrencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.netPriceAmount, netPriceAmount) == 0 && Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(netPriceCurrencyCode, product.netPriceCurrencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, netPriceAmount, netPriceCurrencyCode);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", netPriceAmount=" + netPriceAmount +
            ", netPriceCurrencyCode='" + netPriceCurrencyCode + '\'' +
            '}';
    }
}
