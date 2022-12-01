package uz.abbos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.abbos.model.Inventory;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findInventoriesBySkuCodeIn(String ...skuCode);
}
