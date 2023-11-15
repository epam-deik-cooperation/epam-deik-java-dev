package com.epam.training.webshop.ui.controller;

import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.ProductDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/products")
    public List<ProductDto> listProduct() {
        return productService.getProductList();
    }
}
