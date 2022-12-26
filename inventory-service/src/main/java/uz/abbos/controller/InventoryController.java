package uz.abbos.controller;

import com.common.commonlibrary.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.abbos.dto.ResponseModel;
import uz.abbos.exceptions.ApplicationException;
import uz.abbos.service.InventoryService;
import uz.abbos.service.ProductClient;

import javax.ws.rs.GET;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/inver")
public class InventoryController {

    private final InventoryService inventoryService;
    private final ProductClient productClient;

    @Autowired
    public InventoryController(InventoryService inventoryService,
                               ProductClient productClient) {
        this.inventoryService = inventoryService;
        this.productClient = productClient;
    }

    @GetMapping
    public ResponseEntity<List<ResponseModel>> isInStock(@RequestParam("sku-code") String skuCode) {
        List<ResponseModel> inStock = inventoryService.isInStock(skuCode);
        return ResponseEntity.ok(inStock);
    }

    @GetMapping(value = "/feign")
    public ResponseEntity<List<CustomResponse>> isInStockFromFeign(@RequestParam("skuCodes") List<String> skuCodes) {
        ResponseEntity<List<CustomResponse>> result = productClient.getAllBySkuCode(skuCodes);
        if (result.equals(Collections.emptyList())) {
            throw new ApplicationException("Some product is not in stock");
        }
        return result;
    }

    @GetMapping("/test")
    public String test(){
        return productClient.test();
    }
}
