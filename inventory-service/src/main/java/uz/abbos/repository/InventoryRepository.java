package uz.abbos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.abbos.model.Inventory;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    Optional<Inventory> findBySkuCode(String skuCode);
}
