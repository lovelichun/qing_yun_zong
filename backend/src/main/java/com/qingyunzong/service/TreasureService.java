package com.qingyunzong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.dto.TreasureDTO;
import com.qingyunzong.entity.Treasure;

import java.util.List;

public interface TreasureService {

    IPage<Treasure> list(Page<Treasure> page, String category);

    Treasure getById(Long id);

    void save(TreasureDTO dto);

    void update(TreasureDTO dto);

    void deleteById(Long id);

    List<Treasure> listByCategory(String category);
}
