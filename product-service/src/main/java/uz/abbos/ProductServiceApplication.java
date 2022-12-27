package uz.abbos;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.abbos.model.Product;
import uz.abbos.repository.ProductRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner runner() {
        return args -> System.getenv().forEach((k, v) -> System.out.println(k + "=" + v));
    }

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            Product firstData = new Product();
            firstData.setName("iPhone 13");
            firstData.setDescription("some text");
            firstData.setPrice(BigDecimal.valueOf(1200.0));
            firstData.setSkuCode("phone_black");
            firstData.setIsInStock(Boolean.FALSE);
            firstData.setQuantity(0);

            Product secondData = new Product();
            secondData.setName("iPhone 14");
            secondData.setDescription("some text");
            secondData.setPrice(BigDecimal.valueOf(1500.0));
            secondData.setSkuCode("phone_red");
            secondData.setIsInStock(Boolean.TRUE);
            secondData.setQuantity(120);

            Product data = new Product();
            data.setName("iPhone 12");
            data.setDescription("some text");
            data.setPrice(BigDecimal.valueOf(1500.0));
            data.setSkuCode("phone_blue");
            data.setIsInStock(Boolean.TRUE);
            data.setQuantity(120);

            productRepository.save(firstData);
            productRepository.save(secondData);
            productRepository.save(data);
        };
    }


}
