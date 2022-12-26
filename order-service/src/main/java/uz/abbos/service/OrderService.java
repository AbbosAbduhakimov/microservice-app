package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.abbos.dto.InventoryResponse;
import uz.abbos.dto.OrderDto;
import uz.abbos.event.OrderPlacedEvent;
import uz.abbos.exceptions.ExceptionFromInventoryService;
import uz.abbos.mapper.OrderItemsMapper;
import uz.abbos.mapper.OrderMapper;
import uz.abbos.model.Order;
import uz.abbos.model.OrderItems;
import uz.abbos.repository.OrderRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final OrderItemsMapper orderItemsMapper;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder,
                        KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate, OrderItemsMapper orderItemsMapper, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
        this.orderItemsMapper = orderItemsMapper;
        this.orderMapper = orderMapper;
    }

    public String createOrder(OrderDto request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems = request.getOrderItems()
                .stream().map(orderItemsMapper::orderItemToEntity).collect(Collectors.toList());

        List<String> skuCodes = orderItems.stream()
                .map(OrderItems::getSkuCode)
                .collect(Collectors.toList());
        orderItems.forEach(orderItem -> {
            order.setOrderItems(orderItems);
            orderItem.setOrder(order);
        });

        InventoryResponse[] inventoryResponsesArray =
                webClientBuilder.build().get()
                        .uri("http://inventory-service/inver/feign",
                                uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        if (inventoryResponsesArray == null) {
            log.warn("Order failed");
            throw new ExceptionFromInventoryService("Products is not in stock please try again,or choose another product");
        }
        // if given by skuCodes response from inverService all matches returned true
        boolean result = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::getIsInStock);

        if (result) {
            orderRepository.save(order);
//            kafkaTemplate.send("notificationTopic", "orderKey", new OrderPlacedEvent(order.getOrderNumber()));
            log.info("Order {} is saved", order.getId());
            return "Created Order";
        } else {
            log.warn("Order failed");
            throw new ExceptionFromInventoryService("Some product is not in stock please choose another product");
        }
    }

    public List<OrderDto> getAllOrder() {
        return orderRepository.findAll().stream().map(orderMapper::orderToDto).collect(Collectors.toList());
    }
}
