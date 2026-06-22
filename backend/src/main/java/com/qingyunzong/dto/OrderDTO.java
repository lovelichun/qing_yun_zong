package com.qingyunzong.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {

    private Long id;

    @NotBlank(message = "宝物类别不能为空")
    private String category;

    @NotBlank(message = "宝物级别不能为空")
    private String level;

    @NotBlank(message = "宝物品阶不能为空")
    private String grade;

    @NotBlank(message = "宝物名称不能为空")
    private String name;

    @NotNull(message = "宝物数量不能为空")
    private Integer quantity;

    private String description;
}
