package uz.abbos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.abbos.dto.InventoryResponse;
import uz.abbos.dto.OrderChildDto;
import uz.abbos.dto.OrderDto;
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

    @Autowired
    public OrderService(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public void createOrder(OrderDto request) {
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

        // if given by skuCodes response from inverService all matches returned true
        boolean result = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::getIsInStock);

        if (result) {
            orderRepository.save(order);
            log.info("Order {} is saved", order.getId());
        }else {
            log.info("Order failed");
            throw new IllegalArgumentException("Products is not inStock please try again,or choose another product");
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
