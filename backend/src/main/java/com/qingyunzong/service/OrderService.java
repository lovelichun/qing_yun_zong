package com.qingyunzong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.dto.OrderDTO;
import com.qingyunzong.entity.Order;

public interface OrderService {

    IPage<Order> list(Page<Order> page);

    Order getById(Long id);

    void save(OrderDTO dto);

    void update(OrderDTO dto);

    void deleteById(Long id);
}
