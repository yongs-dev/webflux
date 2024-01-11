package com.mark.webfluxdemo.webtestclient;

import com.mark.webfluxdemo.config.RequestHandler;
import com.mark.webfluxdemo.config.RouterConfig;
import com.mark.webfluxdemo.dto.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.assertj.core.api.Assertions.assertThat;

@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = RouterConfig.class)
public class RouterFunctionTest {

    private WebTestClient client;

    @Autowired
    private RouterConfig config;

    @MockBean
    private RequestHandler handler;

    @BeforeAll
    public void setClient() {
        this.client = WebTestClient.bindToRouterFunction(config.highLevelRouter()).build();
        WebTestClient.bindToServer().baseUrl("http://localhost:8088").build();
    }

    @Test
    public void routerFunctionTest() {
        Mockito.when(handler.squareHandler(Mockito.any())).thenReturn(ServerResponse.ok().bodyValue(new Response(225)));

        this.client
                .get()
                .uri("/router/square/{input}", 15)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Response.class)
                .value(r -> assertThat(r.getOutput()).isEqualTo(225));
    }
}
