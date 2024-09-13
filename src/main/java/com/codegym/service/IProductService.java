package com.codegym.service;

import com.codegym.model.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
}
