package com.epam.training.webshop.ui.command;

import com.epam.training.webshop.core.cart.Cart;
import com.epam.training.webshop.core.checkout.CheckoutService;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.Optional;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CartCommand {

    private final ProductService productService;
    private final CheckoutService checkoutService;
    private final Cart cart;

    public CartCommand(ProductService productService, CheckoutService checkoutService, Cart cart) {
        this.productService = productService;
        this.checkoutService = checkoutService;
        this.cart = cart;
    }

    @ShellMethod(key = "user cart checkout", value = "Checkout the cart")
    public String checkoutCart() {
        return "Your order: " + checkoutService.checkout(cart);
    }

    @ShellMethod(key = "user cart addProduct", value = "Add product to cart")
    public String addProductToCart(String productName) {
        String resultString = productName + " is added to your cart";
        Optional<ProductDto> optionalProduct = productService.getProductByName(productName);
        if (optionalProduct.isEmpty()) {
            resultString = productName + " is not found as a Product";
        } else {
            cart.add(optionalProduct.get());
        }
        return resultString;
    }
}
