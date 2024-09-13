package com.codegym.service.impl;

import com.codegym.model.dto.OrderDTO;
import com.codegym.model.entity.Order;
import com.codegym.repository.IOrderRepository;
import com.codegym.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Page<Order> findAllByDate(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return orderRepository.findAllByDate(startDate, endDate, pageable);
    }
}
