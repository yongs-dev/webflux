package com.mark.webfluxdemo.webclient;

import com.mark.webfluxdemo.dto.InputFailedValidationResponse;
import com.mark.webfluxdemo.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ExchangeTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    // exchange = retrieve + additional info http status code
    @Test
    public void exchangeTest() {
        Mono<Object> responseMono = this.webClient
                .get()
                .uri("reactive-math/square/{input}/error", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(System.out::println)
                .doOnError(err -> System.out.println("!23123" + err.getMessage()));

        StepVerifier.create(responseMono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(
                clientResponse.statusCode() == HttpStatus.BAD_REQUEST
                        ? InputFailedValidationResponse.class
                        : Response.class
                );
    }
}
