package uz.abbos.mapper;

import org.mapstruct.Mapper;
import uz.abbos.dto.OrderItemsDto;
import uz.abbos.model.OrderItems;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface OrderItemsMapper{
    OrderItemsDto orderItemToDto(OrderItems orderItem);
    OrderItems orderItemToEntity(OrderItemsDto orderItem);

    List<OrderItemsDto> orderItemsToDto(List<OrderItems> orderItems);
    List<OrderItems> orderItemsToEntity(List<OrderItemsDto> orderItems);

}
