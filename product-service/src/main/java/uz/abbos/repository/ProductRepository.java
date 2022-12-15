package uz.abbos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.abbos.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {

    List<Product> findAllBySkuCode(String[] skuCode);
}
