package com.mark.orderservice.dto;

import com.mark.productservice.dto.ProductDto;
import com.mark.userservice.dto.TransactionRequestDto;
import com.mark.userservice.dto.TransactionResponseDto;
import lombok.Data;

@Data
public class RequestContext {

    private PurchaseOrderRequestDto purchaseOrderRequestDto;
    private ProductDto productDto;
    private TransactionRequestDto transactionRequestDto;
    private TransactionResponseDto transactionResponseDto;

    public RequestContext(PurchaseOrderRequestDto purchaseOrderRequestDto) {
        this.purchaseOrderRequestDto = purchaseOrderRequestDto;
    }
}
