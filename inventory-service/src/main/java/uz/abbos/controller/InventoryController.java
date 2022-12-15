package uz.abbos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abbos.dto.ResponseModel;
import uz.abbos.service.InventoryService;
import uz.abbos.service.ProductClient;

import java.util.List;

@RestController
@RequestMapping("/inver")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductClient productClient;

    @Autowired
    public InventoryController(InventoryService inventoryService, ProductClient productClient) {
        this.inventoryService = inventoryService;
        this.productClient = productClient;
    }

    @GetMapping
    public ResponseEntity<List<ResponseModel>> isInStock(@RequestParam("sku-code") String ...skuCode){
        List<ResponseModel> inStock = inventoryService.isInStock(skuCode);
        return ResponseEntity.ok(inStock);
    }
    @GetMapping("/feign")
    public List<ResponseModel> isInStockFromFeign(@RequestParam("code") String ...skuCode){
        List<ResponseModel> result = productClient.getAllBySkuCode(skuCode);
        if (result.isEmpty()){
            return null;
        }
        return result;
    }
}
