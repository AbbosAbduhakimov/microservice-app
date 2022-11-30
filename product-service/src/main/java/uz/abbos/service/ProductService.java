package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.abbos.dto.ProductDto;
import uz.abbos.model.Product;
import uz.abbos.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public void createProduct(ProductDto request) {
        Product result = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();

        productRepository.save(result);
        log.info("Product {} is saved", result.getId());
    }


    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        log.info("Quantities of products {}",products.size());
        return products.stream().map(this::mapEntityTo).collect(Collectors.toList());
    }

    private ProductDto mapEntityTo(Product product) {
        return ProductDto
                .builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
