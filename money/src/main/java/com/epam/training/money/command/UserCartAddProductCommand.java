package com.epam.training.money.command;

import com.epam.training.money.product.Product;
import com.epam.training.money.product.ProductService;
import com.epam.training.money.session.Session;
import java.util.Optional;

public class UserCartAddProductCommand extends AbstractCommand{

  private ProductService productService;

  public UserCartAddProductCommand(ProductService productService) {
    super("user", "cart", "addProduct");
    this.productService = productService;
  }

  @Override
  protected String process(String[] commands) {
    String productName = commands[0];
    int amount = Integer.parseInt(commands[1]);

    Optional<Product> product = productService.getProductByName(productName);

    if (product.isEmpty()) {
      return productName + " is not found!";
    } else {
      Session.INSTANCE.getCart().add(product.get(), amount);
      return productName + " added to cart!";
    }
  }
}
