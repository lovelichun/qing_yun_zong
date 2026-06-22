package com.qingyunzong.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.dto.OrderDTO;
import com.qingyunzong.entity.Order;
import com.qingyunzong.mapper.OrderMapper;
import com.qingyunzong.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Override
    public IPage<Order> list(Page<Order> page) {
        return orderMapper.selectPage(page, null);
    }

    @Override
    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public void save(OrderDTO dto) {
        Order order = new Order();
        order.setCategory(dto.getCategory());
        order.setLevel(dto.getLevel());
        order.setGrade(dto.getGrade());
        order.setName(dto.getName());
        order.setQuantity(dto.getQuantity());
        order.setDescription(dto.getDescription());
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.insert(order);
    }

    @Override
    public void update(OrderDTO dto) {
        Order order = orderMapper.selectById(dto.getId());
        if (order != null) {
            order.setCategory(dto.getCategory());
            order.setLevel(dto.getLevel());
            order.setGrade(dto.getGrade());
            order.setName(dto.getName());
            order.setQuantity(dto.getQuantity());
            order.setDescription(dto.getDescription());
            order.setUpdateTime(LocalDateTime.now());
            orderMapper.updateById(order);
        }
    }

    @Override
    public void deleteById(Long id) {
        orderMapper.deleteById(id);
    }
}
