package com.epam.training.webshop.core.checkout.persistence.entity;

import com.epam.training.webshop.core.user.persistence.entity.User;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItemList;
    @ManyToOne
    private User user;
    private Double netPriceAmount;
    private String netPriceCurrencyCode;
    private Double grossPriceAmount;
    private String grossPriceCurrencyCode;
}
