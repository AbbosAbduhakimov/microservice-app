package uz.abbos.dto;


import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;
    private String skuCode;
    private Boolean isInStock;

    private Integer quantity;
}
