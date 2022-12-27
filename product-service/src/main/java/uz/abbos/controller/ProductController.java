package uz.abbos.controller;

import com.common.commonlibrary.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abbos.dto.ProductDto;
import uz.abbos.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@RefreshScope
public class ProductController {

    private final ProductService productService;
    private final Environment environment;

    @Autowired
    public ProductController(ProductService productService, Environment environment) {
        this.productService = productService;
        this.environment = environment;
    }


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ProductDto request) {
        productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAllProductList());
    }

    @GetMapping( "/feign")
    public ResponseEntity<List<CustomResponse>> getAllBySkuCode(@RequestParam("skuCodes") List<String> skuCode) {
        return new ResponseEntity<>(productService.getAllBySkuCode(skuCode), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return "test success";
    }


    @GetMapping("/value")
    String read() {
        return this.readValue();
    }

    @EventListener({
            RefreshRemoteApplicationEvent.class,
            ApplicationReadyEvent.class,
            RefreshScopeRefreshedEvent.class})
    public void refresh() {
        System.out.println("the new value is " + this.readValue());
    }

    private String readValue() {
        return this.environment.getProperty("message");
    }

}
