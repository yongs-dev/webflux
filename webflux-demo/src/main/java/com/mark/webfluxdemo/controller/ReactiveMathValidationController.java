package com.mark.webfluxdemo.controller;

import com.mark.webfluxdemo.dto.Response;
import com.mark.webfluxdemo.exception.InputValidationException;
import com.mark.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathValidationController {

    private final ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}/error")
    public Mono<Response> findSquareError(@PathVariable int input) {
        if (input < 10 || input > 20) {
            throw new InputValidationException(input);
        }

        return this.reactiveMathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> findSquareMonoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if (integer >= 10 && integer <= 20) {
                        sink.next(integer);
                    } else {
                        sink.error(new InputValidationException(integer));
                    }
                })
                .cast(Integer.class)
                .flatMap(this.reactiveMathService::findSquare);
    }

    @GetMapping("square/{input}/assignment")
    public Mono<ResponseEntity<Response>> findSquareAssignment(@PathVariable int input) {
        return Mono.just(input)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(this.reactiveMathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
