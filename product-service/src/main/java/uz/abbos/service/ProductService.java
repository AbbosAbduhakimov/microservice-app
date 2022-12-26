package uz.abbos.service;

import com.common.commonlibrary.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.abbos.dto.ProductDto;
import uz.abbos.exceptions.ApplicationException;
import uz.abbos.model.Product;
import uz.abbos.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@FeignClient("product-service")
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
    public List<ProductDto> getAllProductList() {
        List<Product> products = productRepository.findAll();
        log.info("Quantities of products {}", products.size());
        return products.stream().map(this::mapEntityTo).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomResponse> getAllBySkuCode(List<String> skuCodes) {
        System.out.println(skuCodes);
        List<Product> products = productRepository.findBySkuCodeIn(skuCodes);
        if (products.isEmpty()) {
            log.warn("Product list is empty");
            throw new ApplicationException("Products is not available");
        }
        log.info("Quantities of products {}", products.size());
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

    private CustomResponse mapEntityToResponse(Product product) {
        return new CustomResponse(product.getSkuCode(), product.getIsInStock());
    }
}
