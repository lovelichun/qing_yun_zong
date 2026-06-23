package com.qingyunzong.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.common.Result;
import com.qingyunzong.dto.MenuDTO;
import com.qingyunzong.entity.Menu;
import com.qingyunzong.service.MenuService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @SaCheckPermission("menu:query")
    @GetMapping("/list")
    public Result<IPage<Menu>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        IPage<Menu> result = menuService.list(page);
        return Result.success(result);
    }

    @SaCheckPermission("menu:query")
    @GetMapping("/{id}")
    public Result<Menu> getById(@PathVariable Long id) {
        return Result.success(menuService.getById(id));
    }

    @SaCheckPermission("menu:add")
    @PostMapping
    public Result<Void> save(@Valid @RequestBody MenuDTO dto) {
        menuService.save(dto);
        return Result.success(null);
    }

    @SaCheckPermission("menu:update")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody MenuDTO dto) {
        menuService.update(dto);
        return Result.success(null);
    }

    @SaCheckPermission("menu:delete")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        menuService.deleteById(id);
        return Result.success(null);
    }

    @GetMapping("/all")
    public Result<List<Menu>> listAll() {
        return Result.success(menuService.listAll());
    }
}