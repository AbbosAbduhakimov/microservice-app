package uz.abbos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.abbos.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {


}
