package com.qingyunzong.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("treasure_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String category;

    private String level;

    private String grade;

    private String name;

    private Integer quantity;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
