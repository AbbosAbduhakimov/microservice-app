package uz.abbos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.abbos.service.InventoryService;

@RestController
@RequestMapping("/inver")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{sku-code}")
    public ResponseEntity<Boolean> isInStock(@PathVariable("sku-code") String skuCode){
        return ResponseEntity.ok(inventoryService.isInStock(skuCode));
    }
}
