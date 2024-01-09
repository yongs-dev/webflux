package com.mark.orderservice.util;

import com.mark.orderservice.dto.OrderStatus;
import com.mark.orderservice.dto.PurchaseOrderResponseDto;
import com.mark.orderservice.dto.RequestContext;
import com.mark.orderservice.entity.PurchaseOrder;
import com.mark.userservice.dto.TransactionRequestDto;
import com.mark.userservice.dto.TransactionStatus;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static void setTransactionRequestDto(RequestContext context) {
        TransactionRequestDto dto = new TransactionRequestDto();
        dto.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        dto.setAmount(context.getProductDto().getPrice());
        context.setTransactionRequestDto(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext context) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(context.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(context.getProductDto().getPrice());
        purchaseOrder.setStatus(
                TransactionStatus.APPROVED.equals(context.getTransactionResponseDto().getStatus())
                        ? OrderStatus.COMPLETED
                        : OrderStatus.FAILED
        );

        return purchaseOrder;
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDto responseDto = new PurchaseOrderResponseDto();
        BeanUtils.copyProperties(purchaseOrder, responseDto);
        responseDto.setOrderId(purchaseOrder.getId());
        return responseDto;
    }
}
