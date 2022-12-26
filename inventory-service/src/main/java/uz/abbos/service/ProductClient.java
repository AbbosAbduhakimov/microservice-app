package uz.abbos.service;


import com.common.commonlibrary.CustomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "product-service",path = "/product")
public interface ProductClient {
    @GetMapping(value = "/feign")
    ResponseEntity<List<CustomResponse>> getAllBySkuCode(@RequestParam("skuCodes") List<String> skuCode);

    @GetMapping(value = "/test")
    String test();

}
