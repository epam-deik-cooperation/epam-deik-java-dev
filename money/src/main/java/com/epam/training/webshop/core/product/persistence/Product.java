package com.epam.training.webshop.core.product.persistence;

import com.epam.training.webshop.core.product.model.ProductDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    private Double netPriceAmount;
    private String netPriceCurrencyCode;

    public Product(ProductDto productDto) {
        this.name = productDto.getName();
        this.netPriceAmount = productDto.getNetPrice().amount();
        this.netPriceCurrencyCode = productDto.getNetPrice().currency().getCurrencyCode();
    }

    public Product(String name, Double netPriceAmount, String netPriceCurrencyCode) {
        this.name = name;
        this.netPriceAmount = netPriceAmount;
        this.netPriceCurrencyCode = netPriceCurrencyCode;
    }
}
