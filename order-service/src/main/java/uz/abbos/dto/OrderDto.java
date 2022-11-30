package uz.abbos.dto;

import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter @Setter
public class OrderDto {

    private String orderNumber;

    private List<OrderChildDto> orderItems;

}
