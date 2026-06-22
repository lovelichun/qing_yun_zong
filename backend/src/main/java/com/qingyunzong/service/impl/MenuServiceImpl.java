package com.qingyunzong.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.dto.MenuDTO;
import com.qingyunzong.entity.Menu;
import com.qingyunzong.mapper.MenuMapper;
import com.qingyunzong.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public IPage<Menu> list(Page<Menu> page) {
        return menuMapper.selectPage(page, null);
    }

    @Override
    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void save(MenuDTO dto) {
        Menu menu = new Menu();
        menu.setParentId(dto.getParentId());
        menu.setMenuName(dto.getMenuName());
        menu.setPath(dto.getPath());
        menu.setComponent(dto.getComponent());
        menu.setIcon(dto.getIcon());
        menu.setSortOrder(dto.getSortOrder());
        menu.setStatus(dto.getStatus());
        menu.setCreateTime(LocalDateTime.now());
        menu.setUpdateTime(LocalDateTime.now());
        menuMapper.insert(menu);
    }

    @Override
    public void update(MenuDTO dto) {
        Menu menu = menuMapper.selectById(dto.getId());
        if (menu != null) {
            menu.setParentId(dto.getParentId());
            menu.setMenuName(dto.getMenuName());
            menu.setPath(dto.getPath());
            menu.setComponent(dto.getComponent());
            menu.setIcon(dto.getIcon());
            menu.setSortOrder(dto.getSortOrder());
            menu.setStatus(dto.getStatus());
            menu.setUpdateTime(LocalDateTime.now());
            menuMapper.updateById(menu);
        }
    }

    @Override
    public void deleteById(Long id) {
        menuMapper.deleteById(id);
    }

    @Override
    public List<Menu> listAll() {
        return menuMapper.selectList(null);
    }
}
