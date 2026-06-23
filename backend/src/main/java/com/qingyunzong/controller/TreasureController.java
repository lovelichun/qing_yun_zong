package com.qingyunzong.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.common.Result;
import com.qingyunzong.dto.TreasureDTO;
import com.qingyunzong.entity.Treasure;
import com.qingyunzong.service.TreasureService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/treasure")
public class TreasureController {

    @Resource
    private TreasureService treasureService;

    @SaCheckPermission("treasure:manage")
    @GetMapping("/list")
    public Result<IPage<Treasure>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String category) {
        Page<Treasure> page = new Page<>(pageNum, pageSize);
        IPage<Treasure> result = treasureService.list(page, category);
        return Result.success(result);
    }

    @SaCheckPermission("treasure:manage")
    @GetMapping("/{id}")
    public Result<Treasure> getById(@PathVariable Long id) {
        return Result.success(treasureService.getById(id));
    }

    @SaCheckPermission("treasure:manage")
    @PostMapping
    public Result<Void> save(@Valid @RequestBody TreasureDTO dto) {
        treasureService.save(dto);
        return Result.success(null);
    }

    @SaCheckPermission("treasure:manage")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody TreasureDTO dto) {
        treasureService.update(dto);
        return Result.success(null);
    }

    @SaCheckPermission("treasure:manage")
    @DeleteMapping("/{id}")
    public Result<Void> deleteById(@PathVariable Long id) {
        treasureService.deleteById(id);
        return Result.success(null);
    }

    @SaCheckPermission("treasure:manage")
    @GetMapping("/category/{category}")
    public Result<List<Treasure>> listByCategory(@PathVariable String category) {
        return Result.success(treasureService.listByCategory(category));
    }
}