package uz.abbos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderPlacedEvent {
    private String orderNumber;

    public OrderPlacedEvent(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
