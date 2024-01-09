package com.mark.orderservice.service;

import com.mark.orderservice.dto.PurchaseOrderResponseDto;
import com.mark.orderservice.repository.PurchaseOrderRepository;
import com.mark.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class OrderQueryService {

    private final PurchaseOrderRepository orderRepository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(int userId) {
        return Flux.fromStream(
                        () -> orderRepository.findByUserId(userId).stream()     // Blocking
                                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                )
                .subscribeOn(Schedulers.boundedElastic());
    }
}
