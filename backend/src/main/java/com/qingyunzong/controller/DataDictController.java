package com.qingyunzong.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.common.Result;
import com.qingyunzong.entity.DataDictEntity;
import com.qingyunzong.service.DataDictService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data-dict")
public class DataDictController {

    @Resource
    private DataDictService dataDictService;

    @GetMapping("/type/{dictType}")
    public Result<List<DataDictEntity>> getDictsByType(@PathVariable String dictType) {
        return Result.success(dataDictService.getDictsByType(dictType));
    }

    @GetMapping("/labels/{dictType}")
    public Result<List<String>> getDictLabels(@PathVariable String dictType) {
        return Result.success(dataDictService.getDictLabels(dictType));
    }

    @GetMapping("/all")
    public Result<Map<String, List<String>>> getAllDicts() {
        Map<String, List<String>> result = new java.util.HashMap<>();
        result.put("categories", dataDictService.getDictLabels("category"));
        result.put("levels", dataDictService.getDictLabels("level"));
        result.put("grades", dataDictService.getDictLabels("grade"));
        result.put("names", dataDictService.getDictLabels("name"));
        return Result.success(result);
    }

    @GetMapping("/list")
    public Result<IPage<DataDictEntity>> listDicts(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String dictType) {
        Page<DataDictEntity> page = new Page<>(pageNum, pageSize);
        return Result.success(dataDictService.listDicts(page, dictType));
    }

    @GetMapping("/{id}")
    public Result<DataDictEntity> getDictById(@PathVariable Long id) {
        return Result.success(dataDictService.getDictById(id));
    }

    @PostMapping
    public Result<Void> saveDict(@RequestBody DataDictEntity dict) {
        dataDictService.saveDict(dict);
        return Result.success(null);
    }

    @PutMapping
    public Result<Void> updateDict(@RequestBody DataDictEntity dict) {
        dataDictService.updateDict(dict);
        return Result.success(null);
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteDict(@PathVariable Long id) {
        dataDictService.deleteDict(id);
        return Result.success(null);
    }
}
