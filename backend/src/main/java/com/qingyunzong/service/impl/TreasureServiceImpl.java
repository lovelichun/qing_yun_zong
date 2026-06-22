package com.qingyunzong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.dto.TreasureDTO;
import com.qingyunzong.entity.Treasure;
import com.qingyunzong.mapper.TreasureMapper;
import com.qingyunzong.service.TreasureService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TreasureServiceImpl implements TreasureService {

    @Resource
    private TreasureMapper treasureMapper;

    @Override
    public IPage<Treasure> list(Page<Treasure> page, String category) {
        return treasureMapper.selectPage(page, 
            new LambdaQueryWrapper<Treasure>()
                .eq(category != null && !category.isEmpty(), Treasure::getCategory, category));
    }

    @Override
    public Treasure getById(Long id) {
        return treasureMapper.selectById(id);
    }

    @Override
    public void save(TreasureDTO dto) {
        Treasure treasure = new Treasure();
        treasure.setCategory(dto.getCategory());
        treasure.setLevel(dto.getLevel());
        treasure.setGrade(dto.getGrade());
        treasure.setName(dto.getName());
        treasure.setQuantity(dto.getQuantity());
        treasure.setDescription(dto.getDescription());
        treasure.setCreateTime(LocalDateTime.now());
        treasure.setUpdateTime(LocalDateTime.now());
        treasureMapper.insert(treasure);
    }

    @Override
    public void update(TreasureDTO dto) {
        Treasure treasure = treasureMapper.selectById(dto.getId());
        if (treasure != null) {
            treasure.setCategory(dto.getCategory());
            treasure.setLevel(dto.getLevel());
            treasure.setGrade(dto.getGrade());
            treasure.setName(dto.getName());
            treasure.setQuantity(dto.getQuantity());
            treasure.setDescription(dto.getDescription());
            treasure.setUpdateTime(LocalDateTime.now());
            treasureMapper.updateById(treasure);
        }
    }

    @Override
    public void deleteById(Long id) {
        treasureMapper.deleteById(id);
    }

    @Override
    public List<Treasure> listByCategory(String category) {
        return treasureMapper.selectList(
            new LambdaQueryWrapper<Treasure>()
                .eq(category != null && !category.isEmpty(), Treasure::getCategory, category));
    }
}
