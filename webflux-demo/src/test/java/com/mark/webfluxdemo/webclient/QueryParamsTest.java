package com.mark.webfluxdemo.webclient;

import com.mark.webfluxdemo.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class QueryParamsTest extends BaseTest {

    @Autowired
    private WebClient webClient;

    String queryString = "http://localhost:8088/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest() {
//        URI uri = UriComponentsBuilder.fromUriString(queryString).build(10, 20);

        Flux<Integer> integerFlux = this.webClient
                .get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(10, 20))
                .retrieve()
                .bodyToFlux(Integer.class)
                .doOnNext(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}
