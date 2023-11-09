package com.epam.training.webshop.core.product;

import com.epam.training.webshop.core.finance.money.Money;
import com.epam.training.webshop.core.product.model.ProductDto;
import com.epam.training.webshop.core.product.persistence.Product;
import com.epam.training.webshop.core.product.persistence.ProductRepository;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getProductList() {
        return productRepository.findAll()
            .stream()
            .map(this::createEntityFromDto)
            .toList();
    }

    @Override
    public Optional<ProductDto> getProductByName(String productName) {
        return createEntityFromDto(productRepository.findByName(productName));
    }

    public void createProduct(ProductDto productDto) {
        Product product = new Product(productDto);
        productRepository.save(product);
    }

    private ProductDto createEntityFromDto(Product product) {
        return ProductDto.builder()
            .withName(product.getName())
            .withNetPrice(
                new Money(product.getNetPriceAmount(), Currency.getInstance(product.getNetPriceCurrencyCode()))
            )
            .build();
    }

    private Optional<ProductDto> createEntityFromDto(Optional<Product> product) {
        return product.map(this::createEntityFromDto);
    }
}
