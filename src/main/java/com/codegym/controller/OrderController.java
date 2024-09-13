package com.codegym.controller;

import com.codegym.model.dto.OrderDTO;
import com.codegym.model.entity.Category;
import com.codegym.model.entity.Order;
import com.codegym.model.entity.Product;
import com.codegym.service.ICategoryService;
import com.codegym.service.IOrderService;
import com.codegym.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {
    private final IOrderService orderService;

    private final ICategoryService categoryService;

    private final IProductService productService;

    public OrderController(IOrderService orderService, ICategoryService categoryService, IProductService productService) {
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getOrderPage(@PageableDefault(value = 2) Pageable pageable,
                               Model model) {
        Page<Order> orders = orderService.findAll(pageable);
        model.addAttribute("orders", orders);
        return "/index";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        Order order = orderService.findById(id);
        List<Category> categories = categoryService.findAll();
        List<Product> products = productService.findAll();
        OrderDTO orderDto = new OrderDTO();
        BeanUtils.copyProperties(order, orderDto);
        model.addAttribute("orderDto",orderDto);
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "/update";
    }
    @PostMapping("/update")
    public String update(@Validated @ModelAttribute("orderDto") OrderDTO orderDto
            , BindingResult bindingResult
            , RedirectAttributes redirect
            , Model model){
        new OrderDTO().validate(orderDto, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            List<Category> categories = categoryService.findAll();
            List<Product> products = productService.findAll();
            model.addAttribute("orderDto",orderDto);
            model.addAttribute("products", products);
            model.addAttribute("categories", categories);
            return "/update";
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        orderService.save(order);
        redirect.addFlashAttribute("message", "Cập nhật sản phẩm thành công");
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(value = 5) Pageable pageable,
                         @RequestParam(name="startDate") LocalDate startDate,
                         @RequestParam(name="endDate") LocalDate endDate,
                         Model model){
        if(startDate.isAfter(endDate)){
            Page<Order> orders = orderService.findAll(pageable);
            model.addAttribute("orders", orders);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("message", "Vui lòng chọn lại ngày");
            return "/index";
        }
        Page<Order> orders = orderService.findAllByDate(startDate, endDate, pageable);
        model.addAttribute("orders", orders);
        return "/search";
    }
}
