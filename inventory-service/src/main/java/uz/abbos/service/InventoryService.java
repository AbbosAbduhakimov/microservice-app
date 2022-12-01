package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.abbos.dto.ResponseModel;
import uz.abbos.repository.InventoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<ResponseModel> isInStock(String... skuCode) {
        return inventoryRepository.findInventoriesBySkuCodeIn(skuCode)
                .stream().map(inventory -> ResponseModel.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build()).collect(Collectors.toList());
    }
}
