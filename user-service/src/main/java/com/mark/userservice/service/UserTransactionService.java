package com.mark.userservice.service;

import com.mark.userservice.dto.TransactionRequestDto;
import com.mark.userservice.dto.TransactionResponseDto;
import com.mark.userservice.dto.TransactionStatus;
import com.mark.userservice.entity.UserTransaction;
import com.mark.userservice.repository.UserRepository;
import com.mark.userservice.repository.UserTransactionRepository;
import com.mark.userservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserTransactionService {

    private final UserRepository userRepository;
    private final UserTransactionRepository transactionRepository;

    public Mono<TransactionResponseDto> createTransaction(TransactionRequestDto requestDto) {
        return this.userRepository.updateUserBalance(requestDto.getUserId(), requestDto.getAmount())
                .filter(Boolean::booleanValue)
                .map(b -> EntityDtoUtil.toEntity(requestDto))
                .flatMap(this.transactionRepository::save)
                .map(ut -> EntityDtoUtil.toDto(requestDto, TransactionStatus.APPROVED))
                .defaultIfEmpty(EntityDtoUtil.toDto(requestDto, TransactionStatus.DECLINED));
    }

    public Flux<UserTransaction> getByUserId(int userId) {
        return this.transactionRepository.findByUserId(userId);
    }
}
