package uz.abbos.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.abbos.dto.OrderDto;
import uz.abbos.exceptions.ExceptionFromInventoryService;
import uz.abbos.service.OrderService;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @CircuitBreaker(name = "inventory",fallbackMethod = "fallbackMethod")
//    @TimeLimiter(name = "inventory",fallbackMethod = "fallbackMethod")
//    @Retry(name = "inventory",fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> create(@RequestBody OrderDto request) {
        orderService.createOrder(request);
        return ResponseEntity.ok("Order created");
    }

    public ResponseEntity<String> fallbackMethod(OrderDto orderDto, ExceptionFromInventoryService exception) {
        log.error("Inside circuit breaker fallbackMethod, cause - {}", exception.toString());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> fallbackMethod(OrderDto orderDto, RuntimeException exception) {
        log.error("Inside circuit breaker fallbackMethod, cause - {}", exception.toString());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
