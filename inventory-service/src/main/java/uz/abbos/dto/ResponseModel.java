package uz.abbos.dto;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {

    private String skuCode;
    private Boolean isInStock;
}
