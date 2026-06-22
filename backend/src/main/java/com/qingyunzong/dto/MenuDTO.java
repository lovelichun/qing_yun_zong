package com.qingyunzong.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MenuDTO {

    private Long id;

    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    private String path;

    private String component;

    private String icon;

    private Integer sortOrder;

    private Integer status;
}
