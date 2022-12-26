package uz.abbos.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {

    private Long id;
    private String orderNumber;

    private List<OrderItemsDto> orderItems;

}
