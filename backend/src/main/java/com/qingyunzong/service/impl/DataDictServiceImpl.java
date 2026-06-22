package com.qingyunzong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.entity.DataDictEntity;
import com.qingyunzong.mapper.DataDictMapper;
import com.qingyunzong.service.DataDictService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataDictServiceImpl implements DataDictService {

    @Resource
    private DataDictMapper dataDictMapper;

    @Override
    public List<DataDictEntity> getDictsByType(String dictType) {
        LambdaQueryWrapper<DataDictEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DataDictEntity::getDictType, dictType)
               .orderByAsc(DataDictEntity::getSortOrder);
        return dataDictMapper.selectList(wrapper);
    }

    @Override
    public List<String> getDictLabels(String dictType) {
        List<DataDictEntity> dicts = getDictsByType(dictType);
        return dicts.stream()
                .map(DataDictEntity::getDictLabel)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<DataDictEntity> listDicts(Page<DataDictEntity> page, String dictType) {
        LambdaQueryWrapper<DataDictEntity> wrapper = new LambdaQueryWrapper<>();
        if (dictType != null && !dictType.isEmpty()) {
            wrapper.eq(DataDictEntity::getDictType, dictType);
        }
        wrapper.orderByAsc(DataDictEntity::getDictType, DataDictEntity::getSortOrder);
        return dataDictMapper.selectPage(page, wrapper);
    }

    @Override
    public DataDictEntity getDictById(Long id) {
        return dataDictMapper.selectById(id);
    }

    @Override
    public void saveDict(DataDictEntity dict) {
        dataDictMapper.insert(dict);
    }

    @Override
    public void updateDict(DataDictEntity dict) {
        dataDictMapper.updateById(dict);
    }

    @Override
    public void deleteDict(Long id) {
        dataDictMapper.deleteById(id);
    }
}
