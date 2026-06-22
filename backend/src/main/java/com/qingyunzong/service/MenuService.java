package com.qingyunzong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.dto.MenuDTO;
import com.qingyunzong.entity.Menu;

import java.util.List;

public interface MenuService {

    IPage<Menu> list(Page<Menu> page);

    Menu getById(Long id);

    void save(MenuDTO dto);

    void update(MenuDTO dto);

    void deleteById(Long id);

    List<Menu> listAll();
}
