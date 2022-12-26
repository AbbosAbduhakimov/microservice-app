package uz.abbos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class OrderItemsDto {

    private Long id;
    private String skuCode;

    private BigDecimal price;

    private Integer quantity;
}
