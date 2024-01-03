package com.mark.webfluxdemo.config;

import com.mark.webfluxdemo.dto.InputFailedValidationResponse;
import com.mark.webfluxdemo.exception.InputValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final RequestHandler handler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("router", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("square/{input}", req -> {
                    int input = Integer.parseInt(req.pathVariable("input"));
                    if (input >= 10 && input <= 19) {
                        return handler.squareHandler(req);
                    } else {
                        return ServerResponse.badRequest().bodyValue("only 10-19 allowed");
                    }
                })
                .GET("table/{input}", handler::tableHandler)
                .GET("table/{input}/stream", handler::tableStreamHandler)
                .POST("multiply", handler::multiplyHandler)
                .GET("square/{input}/validation", handler::squareHandlerWithValidation)
                .onError(InputValidationException.class, exceptionHandler())
                .build();
    }

    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler(){
        return (err, req) -> {
            InputValidationException ex = (InputValidationException) err;
            InputFailedValidationResponse response = new InputFailedValidationResponse();
            response.setInput(ex.getInput());
            response.setMessage(ex.getMessage());
            response.setErrorCode(InputValidationException.getErrorCode());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
