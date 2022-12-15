package uz.abbos.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.abbos.dto.ResponseModel;

import java.util.List;


@FeignClient(name = "product-service",url = "http://localhost:8081/product")
public interface ProductClient {
    @GetMapping("/feign")
    List<ResponseModel> getAllBySkuCode(@RequestParam("code") String ...skuCode);
}
