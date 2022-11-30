package uz.abbos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import uz.abbos.model.Product;

public interface ProductRepository extends MongoRepository<Product,String> {
}
