package com.qingyunzong.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.common.Result;
import com.qingyunzong.dto.OrderDTO;
import com.qingyunzong.entity.Order;
import com.qingyunzong.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @SaCheckPermission("order:query")
    @GetMapping("/list")
    public Result<IPage<Order>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        IPage<Order> result = orderService.list(page);
        return Result.success(result);
    }

    @SaCheckPermission("order:query")
    @GetMapping("/{id}")
    public Result<Order> getById(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @SaCheckPermission("order:add")
    @PostMapping
    public Result<Void> save(@Valid @RequestBody OrderDTO dto) {
        orderService.save(dto);
        return Result.success(null);
    }

    @SaCheckPermission("order:update")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody OrderDTO dto) {
        orderService.update(dto);
        return Result.success(null);
    }

    @SaCheckPermission("order:delete")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
        return Result.success(null);
    }
}