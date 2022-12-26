package uz.abbos.mapper;

import org.mapstruct.Mapper;
import uz.abbos.dto.OrderDto;
import uz.abbos.model.Order;

@Mapper(
        componentModel = "spring"
)
public interface OrderMapper {
    OrderDto orderToDto(Order order);
}
