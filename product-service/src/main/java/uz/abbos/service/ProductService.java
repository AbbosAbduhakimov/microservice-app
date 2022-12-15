package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.abbos.dto.ProductDto;
import uz.abbos.dto.ResponseModel;
import uz.abbos.model.Product;
import uz.abbos.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
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
                .isInStock(Boolean.TRUE)
                .skuCode(request.getSkuCode())
                .build();

        productRepository.save(result);
        log.info("Product {} is saved", result.getId());
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        List<Product> products = productRepository.findAll();
        log.info("Quantities of products {}",products.size());
        return products.stream().map(this::mapEntityTo).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseModel> getAllBySkuCode(String ...skuCode) {
        List<Product> products = productRepository.findAllBySkuCode(skuCode);
        log.info("Quantities of products {}",products.size());
        return products.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

    private ProductDto mapEntityTo(Product product) {
        return ProductDto
                .builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .skuCode(product.getSkuCode())
                .isInStock(product.getIsInStock())
                .quantity(product.getQuantity())
                .build();
    }

    private ResponseModel mapEntityToResponse(Product product) {
        return ResponseModel
                .builder()
                .skuCode(product.getSkuCode())
                .isInStock(product.getIsInStock())
                .build();
    }
}
