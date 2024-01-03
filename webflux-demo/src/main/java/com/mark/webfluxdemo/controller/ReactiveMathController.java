package com.mark.webfluxdemo.controller;

import com.mark.webfluxdemo.dto.MultiplyRequestDto;
import com.mark.webfluxdemo.dto.Response;
import com.mark.webfluxdemo.service.ReactiveMathService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("reactive-math")
@RequiredArgsConstructor
public class ReactiveMathController {
    private final ReactiveMathService reactiveMathService;

    @GetMapping("square/{input}")
    public Mono<Response> findSquare(@PathVariable int input) {
        return this.reactiveMathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public Flux<Response> multiplicationTable(@PathVariable int input) {
        return this.reactiveMathService.multiplicationTable(input);
    }

    // Streaming 위해 produces = MediaType.TEXT_EVENT_STREAM_VALUE 설정
    @GetMapping(value = "table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Response> multiplicationTableStream(@PathVariable int input) {
        return this.reactiveMathService.multiplicationTable(input);
    }

    @PostMapping("multiply")
    public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDto> requestDtoMono, @RequestHeader Map<String, String> header) {
        System.out.println(header);
        return this.reactiveMathService.multiply(requestDtoMono);
    }
}
