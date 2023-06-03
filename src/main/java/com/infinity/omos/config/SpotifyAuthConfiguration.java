package com.infinity.omos.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.querydsl.core.util.ArrayUtils;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class SpotifyAuthConfiguration {


    @Value("${spotify.clientId}")
    private String clientId;

    @Value("${spotify.clientSecret}")
    private String clientSecret;

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public SpotifyAuthConfiguration(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }


    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(clientId,clientSecret);
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

//    @Bean
//    public Encoder feignEncoder(ObjectMapper objectMapper) {
//        return (object, type, template) -> {
//            try {
//                template.body(objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
//                        .writeValueAsString(object));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        };
//    }

    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return (response, type) -> objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
                .readValue(response.body().asReader(StandardCharsets.UTF_8), TypeFactory.defaultInstance().constructType(type));
    }

//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return template -> {
//            template.header("headerName", "headerValue");
//            template.header("headerName2", "headerValue2");
//        };
//    }

//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        System.out.println("???");
//        return requestTemplate -> {
//            if(ArrayUtils.isEmpty(new byte[][]{requestTemplate.body()}) && !isGetOrDelete(requestTemplate)) {
//                // body가 비어있는 경우에 요청을 보내면 411 에러가 생김 https://github.com/OpenFeign/feign/issues/1251
//                // content-length로 처리가 안되어서 빈 값을 항상 보내주도록 함
//
//                requestTemplate.body("{}");
//            }
//        };
//    }

    private boolean isGetOrDelete(RequestTemplate requestTemplate) {
        return Request.HttpMethod.GET.name().equals(requestTemplate.method())
                || Request.HttpMethod.DELETE.name().equals(requestTemplate.method());
    }

//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            requestTemplate.header("content-type", "application/x-www-form-urlencoded");
//            //requestTemplate.hea der("Content-length", "0");
//        };
//    }

}
