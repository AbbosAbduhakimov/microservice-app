package uz.abbos.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String skuCode;
    private Integer quantity;
    @Field
    private Boolean isInStock = Boolean.FALSE;

}
