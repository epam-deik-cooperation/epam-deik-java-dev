package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.checkout.CheckoutService;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.Product;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class CartCommand {

    private final Cart cart;
    private final CheckoutService checkoutService;
    private final ProductService productService;

    @ShellMethod(key = "user cart list", value = "List the cart content")
    public String cartList() {
        if (cart.getProductList().isEmpty()) {
            return "The cart is empty!";
        } else {
            return cart.getProductList().toString();
        }
    }

    @ShellMethod(key = "user cart checkout", value = "Checkout the cart")
    public String cartCheckout() {
        if (cart.getProductList().isEmpty()) {
            return "You cannot checkout because cart is empty!";
        } else {
            return "Your order: " + checkoutService.checkout(cart);
        }
    }

    @ShellMethod(key = "user cart clear", value = "Clear the cart")
    public String cartClear() {
        if (cart.getProductList().isEmpty()) {
            return "You cannot clear your cart because it is empty!";
        } else {
            cart.clearCart();
            return "The cart is cleared successfully!";
        }
    }

    @ShellMethod(key = "user cart addProduct", value = "Add product to cart")
    public String addProduct(String productName) {
        Optional<Product> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return productName + " is not found as a Product!";
        } else {
            cart.addProduct(optionalProduct.get());
            return productName + " is added to your cart!";
        }
    }

    @ShellMethod(key = "user cart removeProduct", value = "Remove product from cart")
    public String removeProduct(String productName) {
        Optional<Product> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            return productName + " is not found as a Product!";
        }
        if (cart.getProductList().contains(optionalProduct.get())) {
            cart.removeProduct(optionalProduct.get());
            return productName + " is removed from your cart!";
        } else {
            return productName + " is not in your cart!";
        }
    }
}
