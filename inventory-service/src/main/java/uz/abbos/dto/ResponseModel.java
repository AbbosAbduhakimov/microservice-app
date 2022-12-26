package uz.abbos.dto;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel implements Serializable {
    private static final long serialVersionUID = 123L;
    private String skuCode;
    private Boolean isInStock;
}
