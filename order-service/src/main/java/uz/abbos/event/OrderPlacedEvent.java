package uz.abbos.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
public class OrderPlacedEvent  implements Serializable {
    private String orderNumber;

    public OrderPlacedEvent(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
