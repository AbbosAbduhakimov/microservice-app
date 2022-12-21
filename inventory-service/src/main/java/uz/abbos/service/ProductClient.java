package uz.abbos.service;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.abbos.dto.ResponseModel;

import java.util.List;


@FeignClient(name = "product-service")
@LoadBalancerClient(name = "product-service", configuration = LoadBalancerConfig.class)
public interface ProductClient {
    @GetMapping("/feign")
    ResponseEntity<List<ResponseModel>> getAllBySkuCode(@RequestParam("sku-code") String[] skuCode);

}
