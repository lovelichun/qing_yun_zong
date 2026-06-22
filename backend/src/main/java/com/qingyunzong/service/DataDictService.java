package com.qingyunzong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.entity.DataDictEntity;

import java.util.List;

public interface DataDictService {

    List<DataDictEntity> getDictsByType(String dictType);

    List<String> getDictLabels(String dictType);

    IPage<DataDictEntity> listDicts(Page<DataDictEntity> page, String dictType);

    DataDictEntity getDictById(Long id);

    void saveDict(DataDictEntity dict);

    void updateDict(DataDictEntity dict);

    void deleteDict(Long id);
}
