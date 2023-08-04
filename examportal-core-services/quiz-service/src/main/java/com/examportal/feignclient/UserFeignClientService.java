package com.examportal.feignclient;

import com.examportal.constant.GenericConstants;
import com.examportal.dtos.ApiResponseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(name = "user-service",
        url = "${backendServerDetail}")
public interface UserFeignClientService {


    @GetMapping("/api/v1/user/getUser")
    ApiResponseDTO getUser(@RequestHeader(GenericConstants.AUTHORIZATION) String bearerToken,
                           @RequestParam(name = "id") Long id);

}
