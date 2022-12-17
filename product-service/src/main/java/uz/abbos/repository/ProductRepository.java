package uz.abbos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import uz.abbos.model.Product;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String>, QueryByExampleExecutor<Product> {
    List<Product> findBySkuCodeIn(List<String> skuCodes);
}
