package com.mark.webfluxdemo.config;

import com.mark.webfluxdemo.dto.MultiplyRequestDto;
import com.mark.webfluxdemo.dto.Response;
import com.mark.webfluxdemo.exception.InputValidationException;
import com.mark.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RequestHandler {

    private final ReactiveMathService reactiveMathService;

    public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Response> responseMono = this.reactiveMathService.findSquare(input);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = this.reactiveMathService.multiplicationTable(input);
        return ServerResponse.ok()
                .body(responseFlux, Response.class);
    }

    // Streaming 위해 contextType MediaType.TEXT_EVENT_STREAM 설정
    public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));
        Flux<Response> responseFlux = this.reactiveMathService.multiplicationTable(input);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseFlux, Response.class);
    }

    public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
        Mono<MultiplyRequestDto> multiplyRequestDto = serverRequest.bodyToMono(MultiplyRequestDto.class);
        Mono<Response> responseMono = this.reactiveMathService.multiply(multiplyRequestDto);
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(responseMono, Response.class);
    }

    public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
        int input = Integer.parseInt(serverRequest.pathVariable("input"));

        if (input < 10 || input > 20) {
            return Mono.error(new InputValidationException(input));
        }

        Mono<Response> responseMono = this.reactiveMathService.findSquare(input);
        return ServerResponse.ok()
                .body(responseMono, Response.class);
    }
}
