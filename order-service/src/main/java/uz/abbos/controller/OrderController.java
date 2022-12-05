package uz.abbos.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.abbos.dto.OrderDto;
import uz.abbos.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
    public String create(@RequestBody OrderDto request) {
        orderService.createOrder(request);
        return "Order created";
    }

    public String fallbackMethod(OrderDto orderDto,RuntimeException runtimeException){
        return "Something went wrong,please order after some time";
    }
}
