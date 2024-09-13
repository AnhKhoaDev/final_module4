package com.codegym.model.dto;

import com.codegym.model.entity.Product;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class OrderDTO implements Validator {
    Long id;

    Product product;

    @NotNull(message = "Ngày mua không được để trống")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Future(message = "Ngày mua phải lớn hơn ngày hiện tại")
    LocalDate orderDate;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải là số nguyên dương")
    double quantity;


    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
