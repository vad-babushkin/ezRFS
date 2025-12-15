//package com.iconiux.ezrfs.daemon.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import feign.Client;
//import feign.FeignException;
//import feign.Logger;
//import feign.Request;
//import feign.codec.ErrorDecoder;
//import feign.slf4j.Slf4jLogger;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.WebRequest;
//
//
//@Getter
//@Setter
//@RequiredArgsConstructor
//@Configuration
//@Slf4j
//public class CoreFeignConfiguration {
//	@Value("${feign.client.config.default.connectTimeout:30000}")
//	private int connectTimeout;
//	@Value("${feign.client.config.default.readTimeout:300000}")
//	private int readTimeout;
//
////	private final ObjectMapper objectMapper;
//
//	@Bean
//	Logger.Level feignLoggerLevel() {
//		return Logger.Level.HEADERS;
//	}
//
//	@Bean
//	Logger feignLogger() {
//		return new Slf4jLogger();
//	}
//
////	@Bean
////	public ErrorDecoder getErrorDecoder() {
////		return new UralsibErrorDecoder(objectMapper);
////	}
//
//	@ExceptionHandler(FeignException.class)
//	@ResponseBody
//	public ResponseEntity<Object> handleConstraintViolationException(Exception e, WebRequest request) {
//		return ResponseEntity.accepted().body("");
//	}
//
//	@Bean
//	public Request.Options options() {
//		return new Request.Options(connectTimeout, readTimeout);
//	}
//}
