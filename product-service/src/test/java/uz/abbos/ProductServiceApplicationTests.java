package uz.abbos;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uz.abbos.dto.ProductDto;
import uz.abbos.repository.ProductRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;


    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo:latest");

    static void setProperties(DynamicPropertyRegistry properties){
        properties.add("spring.data.mongodb.uri",container::getConnectionString);
    }

    @Test
    void shouldProductCreate() throws Exception {
        ProductDto product = getProduct();
        String productString = objectMapper.writeValueAsString(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productString))
                .andExpect(status().isCreated());
    }

    private ProductDto getProduct() {
        return ProductDto.builder()
                .name("phone")
                .description("smartphone")
                .price(BigDecimal.valueOf(1200))
                .build();
    }

    @Test
    void shouldProductListNotEmpty(){
        Assertions.assertNotEquals(null, productRepository.findAll());
    }

}
