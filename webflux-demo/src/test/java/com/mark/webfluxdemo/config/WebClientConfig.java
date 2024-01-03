package com.mark.webfluxdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8088")
                // 1. Basic Auth 설정
//                .defaultHeaders(h -> h.setBasicAuth("username", "password"))
                // 2. Bearer Auth Token 설정
                .filter(this::sessionToken)
                .build();
    }

//    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
//        ClientRequest clientRequest = ClientRequest.from(request).headers(h -> h.setBearerAuth("some-lengthy-jwt")).build();
//        return ex.exchange(clientRequest);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction ex) {
        // auth --> auth or oauth
        return ex.exchange(
                request.attribute("auth")
                        .map(v -> v.equals("basic") ? withBasicAuth(request) : withOAuth(request))
                        .orElse(request)
        );
    }

    private ClientRequest withBasicAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBasicAuth("username", "password"))
                .build();
    }

    private ClientRequest withOAuth(ClientRequest request) {
        return ClientRequest.from(request)
                .headers(h -> h.setBearerAuth("some-token"))
                .build();
    }
}