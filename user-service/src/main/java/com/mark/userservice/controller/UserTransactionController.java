package com.mark.userservice.controller;

import com.mark.userservice.dto.TransactionRequestDto;
import com.mark.userservice.dto.TransactionResponseDto;
import com.mark.userservice.entity.UserTransaction;
import com.mark.userservice.service.UserTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final UserTransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(this.transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getByUserId(@RequestParam("userId") int userId) {
        return this.transactionService.getByUserId(userId);
    }
}
