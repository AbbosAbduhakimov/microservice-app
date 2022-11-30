package uz.abbos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import uz.abbos.model.Inventory;
import uz.abbos.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }



    @Bean
    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
        return args -> {
            Inventory firstData = new Inventory();
            firstData.setSkuCode("phone_black");
            firstData.setQuantity(120);

            Inventory secondData = new Inventory();
            secondData.setSkuCode("phone_red");
            secondData.setQuantity(0);


            inventoryRepository.save(firstData);
            inventoryRepository.save(secondData);
        };
    }
}
