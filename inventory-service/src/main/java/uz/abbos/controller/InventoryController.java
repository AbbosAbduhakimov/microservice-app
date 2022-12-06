package uz.abbos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abbos.dto.ResponseModel;
import uz.abbos.service.InventoryService;
import java.util.List;

@RestController
@RequestMapping("/inver")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseModel>> isInStock(@RequestParam("sku-code") String ...skuCode){
        List<ResponseModel> inStock = inventoryService.isInStock(skuCode);
        return ResponseEntity.ok(inStock);
    }
}
