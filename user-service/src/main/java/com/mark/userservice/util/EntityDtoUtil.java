package com.mark.userservice.util;

import com.mark.userservice.dto.TransactionRequestDto;
import com.mark.userservice.dto.TransactionResponseDto;
import com.mark.userservice.dto.TransactionStatus;
import com.mark.userservice.dto.UserDto;
import com.mark.userservice.entity.User;
import com.mark.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDtoUtil {

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }

    public static TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus status) {
        TransactionResponseDto responseDto = new TransactionResponseDto();
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setStatus(status);
        return responseDto;
    }

    public static UserTransaction toEntity(TransactionRequestDto requestDto) {
        UserTransaction ut = new UserTransaction();
        ut.setUserId(requestDto.getUserId());
        ut.setAmount(requestDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

}
