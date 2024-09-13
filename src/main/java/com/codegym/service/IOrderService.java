package com.codegym.service;

import com.codegym.model.dto.OrderDTO;
import com.codegym.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Page<Order> findAll(Pageable pageable);

    Order findById(Long id);

    void save(Order order);
}
