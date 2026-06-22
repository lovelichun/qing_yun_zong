package com.qingyunzong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("data_dict")
public class DataDictEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String dictType;

    private String dictLabel;

    private String dictValue;

    private Integer sortOrder;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
