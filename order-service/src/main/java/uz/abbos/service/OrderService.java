package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.abbos.dto.InventoryResponse;
import uz.abbos.dto.OrderChildDto;
import uz.abbos.dto.OrderDto;
import uz.abbos.event.OrderPlacedEvent;
import uz.abbos.exceptions.ExceptionFromInventoryService;
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
public class OrderService  {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder,
                        KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
        this.kafkaTemplate = kafkaTemplate;
    }
    public String createOrder(OrderDto request) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderItems = request.getOrderItems()
                .stream().map(this::mapEntityTo).collect(Collectors.toList());
        order.setOrderItems(orderItems);

        List<String> skuCodes = order.getOrderItems().stream()
                .map(OrderItems::getSkuCode)
                .collect(Collectors.toList());


        //  call InventoryService,and place order if products is in stock
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/inver",uriBuilder -> uriBuilder.queryParam("sku-code",skuCodes).build())
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
            kafkaTemplate.send("notificationTopic","orderKey",new OrderPlacedEvent(order.getOrderNumber()));
            log.info("Order {} is saved", order.getId());
            return "Created Order";
        }
        else {
            log.warn("Order failed");
            throw new ExceptionFromInventoryService("Some product is not in stock please choose another product");
        }
    }

    private OrderItems mapEntityTo(OrderChildDto childModel) {
        OrderItems orderItems = new OrderItems();

        orderItems.setSkuCode(childModel.getSkuCode());
        orderItems.setPrice(childModel.getPrice());
        orderItems.setQuantity(childModel.getQuantity());
        return orderItems;
    }

}
