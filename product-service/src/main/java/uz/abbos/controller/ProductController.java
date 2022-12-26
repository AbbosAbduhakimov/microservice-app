package uz.abbos.controller;

import com.common.commonlibrary.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abbos.dto.ProductDto;
import uz.abbos.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
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
        for (String s : skuCode) {
            System.out.println("skucodes " + s);
        }
        return new ResponseEntity<>(productService.getAllBySkuCode(skuCode), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String test(){
        return "test success";
    }
}
