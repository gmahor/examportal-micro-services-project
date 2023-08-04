//package com.examportal.feignclient;
//
//import com.examportal.constant.GenericConstants;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Component
//public class FeignClientInterceptor implements RequestInterceptor {
//
//    private final HttpServletRequest httpServletRequest;
//
//    @Autowired
//    public FeignClientInterceptor(HttpServletRequest httpServletRequest) {
//        this.httpServletRequest = httpServletRequest;
//    }
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        String token = httpServletRequest.getHeader(GenericConstants.AUTHORIZATION);
//        if (token != null) {
//            requestTemplate.header(GenericConstants.AUTHORIZATION, token);
//        }
//    }
//}
