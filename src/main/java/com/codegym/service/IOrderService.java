package com.codegym.service;

import com.codegym.model.dto.OrderDTO;
import com.codegym.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IOrderService {
    Page<Order> findAll(Pageable pageable);

    Order findById(Long id);

    void save(Order order);

    Page<Order> findAllByDate(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
