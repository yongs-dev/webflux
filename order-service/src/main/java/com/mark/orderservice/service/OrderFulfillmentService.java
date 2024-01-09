package com.mark.orderservice.service;

import com.mark.orderservice.client.ProductClient;
import com.mark.orderservice.client.UserClient;
import com.mark.orderservice.dto.PurchaseOrderRequestDto;
import com.mark.orderservice.dto.PurchaseOrderResponseDto;
import com.mark.orderservice.dto.RequestContext;
import com.mark.orderservice.repository.PurchaseOrderRepository;
import com.mark.orderservice.util.EntityDtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentService {

    private final PurchaseOrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestDtoMono) {
        return requestDtoMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userRequestResponse)
                .map(EntityDtoUtil::getPurchaseOrder)
                .map(this.orderRepository::save)    // Blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<RequestContext> productRequestResponse(RequestContext context) {
        return this.productClient.getProductById(context.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(context::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(context);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext context) {
        return this.userClient.authorizeTransaction(context.getTransactionRequestDto())
                .doOnNext(context::setTransactionResponseDto)
                .thenReturn(context);
    }
}
