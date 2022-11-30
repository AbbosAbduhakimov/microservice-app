package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.abbos.dto.OrderChildDto;
import uz.abbos.dto.OrderDto;
import uz.abbos.model.Order;
import uz.abbos.model.OrderItems;
import uz.abbos.repository.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(OrderDto request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = request.getOrderItems()
                .stream().map(this::mapEntityTo).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        orderRepository.save(order);
        log.info("Order {} is saved",order.getId());
    }

    private OrderItems mapEntityTo(OrderChildDto childModel) {
        OrderItems orderItems = new OrderItems();

        orderItems.setSkuCode(childModel.getSkuCode());
        orderItems.setPrice(childModel.getPrice());
        orderItems.setQuantity(childModel.getQuantity());
        return orderItems;
    }

}
