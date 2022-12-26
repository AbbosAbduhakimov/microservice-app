package uz.abbos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.abbos.model.OrderItems;

public interface ItemsRepository extends JpaRepository<OrderItems,Integer> {
}
