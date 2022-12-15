package uz.abbos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abbos.dto.ProductDto;
import uz.abbos.dto.ResponseModel;
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
    public ResponseEntity<Void> create(@RequestBody ProductDto request){
         productService.createProduct(request);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/feign")
    public List<ResponseModel> getAllBySkuCode(@RequestParam("sku-code") String ...skuCode){
        return productService.getAllBySkuCode(skuCode);
    }
}
