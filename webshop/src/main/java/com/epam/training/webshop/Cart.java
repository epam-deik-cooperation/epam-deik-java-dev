package com.epam.training.webshop;

import java.util.List;

/*
 * Background:
 *
 *  At the moment, we only allow ordering product via e-mail.
 *  This ticket is to introduce a shopping cart functionality
 *  to make shopping more simple for our customers as well as to
 *  enable having discounts.
 *
 * Acceptance criteria:
 *
 *  It is possible to:
 *  1. add products to the cart
 *  2. list product in the cart
 *  3. remove a single product from the cart
 *  4. add coupons to the cart
 *  4.1. A coupon should change the total value of the cart with a fixed amount
 *  5. list coupons in the cart
 *  6. remove coupons from the cart
 *  7. query the value of the cart in HUF and USD currencies
 *  7.1. this should include the price of the products, coupon discounts, and VAT
 *  7.2. VAT is 27% of the price of the products without any discount
 */
public interface Cart {

    List<Product> listProduct();
    void addProduct(Product product);

}
