package uz.abbos.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModel {

    private String skuCode;
    private Boolean isInStock;
}
