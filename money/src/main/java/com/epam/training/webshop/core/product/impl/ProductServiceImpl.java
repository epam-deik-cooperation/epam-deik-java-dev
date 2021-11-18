package com.epam.training.webshop.core.product.impl;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.ProductService;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.product.persistence.entity.Product;
import com.epam.training.webshop.core.product.persistence.repository.ProductRepository;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getProductList() {
        return productRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> getProductByName(String productName) {
        return convertEntityToDto(productRepository.findByName(productName));
    }

    @Override
    public void createProduct(ProductDto productDto) {
        Objects.requireNonNull(productDto, "Product cannot be null");
        Objects.requireNonNull(productDto.getName(), "Product name cannot be null");
        Objects.requireNonNull(productDto.getNetPrice(), "Product net price cannot be null");
        Product product = new Product(productDto.getName(),
            productDto.getNetPrice().getValue(),
            productDto.getNetPrice().getCurrency().getCurrencyCode());
        productRepository.save(product);
    }

    private ProductDto convertEntityToDto(Product product) {
        return ProductDto.builder()
            .withName(product.getName())
            .withNetPrice(new Money(product.getNetPriceAmount(), Currency.getInstance(product.getNetPriceCurrencyCode())))
            .build();
    }

    private Optional<ProductDto> convertEntityToDto(Optional<Product> product) {
        return product.isEmpty() ? Optional.empty() : Optional.of(convertEntityToDto(product.get()));
    }
}
