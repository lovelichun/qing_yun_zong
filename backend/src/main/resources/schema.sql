CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `status` INT DEFAULT 1 COMMENT '状态 0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
  `description` VARCHAR(200) COMMENT '描述',
  `status` INT DEFAULT 1 COMMENT '状态 0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '权限ID',
  `perm_name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `perm_code` VARCHAR(100) NOT NULL UNIQUE COMMENT '权限编码',
  `description` VARCHAR(200) COMMENT '描述',
  `status` INT DEFAULT 1 COMMENT '状态 0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统权限表';

CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

CREATE TABLE IF NOT EXISTS `sys_role_permission` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT NOT NULL COMMENT '权限ID',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '菜单ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID',
  `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `path` VARCHAR(200) COMMENT '路径',
  `component` VARCHAR(200) COMMENT '组件',
  `icon` VARCHAR(100) COMMENT '图标',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `status` INT DEFAULT 1 COMMENT '状态 0禁用 1启用',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';

CREATE TABLE IF NOT EXISTS `treasure` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '宝物ID',
  `category` VARCHAR(50) NOT NULL COMMENT '类别',
  `level` VARCHAR(50) NOT NULL COMMENT '级别',
  `grade` VARCHAR(50) NOT NULL COMMENT '品阶',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  `quantity` INT DEFAULT 0 COMMENT '数量',
  `description` VARCHAR(500) COMMENT '说明',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宝物表';

CREATE TABLE IF NOT EXISTS `treasure_order` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
  `category` VARCHAR(50) NOT NULL COMMENT '类别',
  `level` VARCHAR(50) NOT NULL COMMENT '级别',
  `grade` VARCHAR(50) NOT NULL COMMENT '品阶',
  `name` VARCHAR(100) NOT NULL COMMENT '名称',
  `quantity` INT DEFAULT 0 COMMENT '数量',
  `description` VARCHAR(500) COMMENT '说明',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';

CREATE TABLE IF NOT EXISTS `data_dict` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '字典ID',
  `dict_type` VARCHAR(50) NOT NULL COMMENT '字典类型',
  `dict_label` VARCHAR(100) NOT NULL COMMENT '字典标签',
  `dict_value` VARCHAR(100) NOT NULL COMMENT '字典值',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `description` VARCHAR(200) COMMENT '描述',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据字典表';

INSERT INTO `data_dict` (`dict_type`, `dict_label`, `dict_value`, `sort_order`, `description`) VALUES 
('category', '法器', '法器', 1, '宝物类别'),
('category', '兵器', '兵器', 2, '宝物类别'),
('category', '丹药', '丹药', 3, '宝物类别'),
('category', '灵材', '灵材', 4, '宝物类别'),
('category', '功法', '功法', 5, '宝物类别'),
('category', '灵石', '灵石', 6, '宝物类别'),
('category', '符箓', '符箓', 7, '宝物类别'),
('level', '凡级', '凡级', 1, '宝物级别'),
('level', '灵级', '灵级', 2, '宝物级别'),
('level', '宝级', '宝级', 3, '宝物级别'),
('level', '仙级', '仙级', 4, '宝物级别'),
('level', '神级', '神级', 5, '宝物级别'),
('grade', '下品', '下品', 1, '宝物品阶'),
('grade', '中品', '中品', 2, '宝物品阶'),
('grade', '上品', '上品', 3, '宝物品阶'),
('grade', '极品', '极品', 4, '宝物品阶'),
('name', '青钢剑', '青钢剑', 1, '宝物名称'),
('name', '灵木弓', '灵木弓', 2, '宝物名称'),
('name', '护心镜', '护心镜', 3, '宝物名称'),
('name', '轻羽扇', '轻羽扇', 4, '宝物名称'),
('name', '传音玉牌', '传音玉牌', 5, '宝物名称'),
('name', '寻灵石盘', '寻灵石盘', 6, '宝物名称'),
('name', '聚气丹', '聚气丹', 7, '宝物名称'),
('name', '止血丹', '止血丹', 8, '宝物名称'),
('name', '符纸', '符纸', 9, '宝物名称');

INSERT INTO `sys_user` (`username`, `password`, `nickname`, `status`) VALUES 
('admin', '123456', '管理员', 1);

INSERT INTO `sys_role` (`role_name`, `role_code`, `description`, `status`) VALUES 
('超级管理员', 'admin', '系统超级管理员', 1),
('普通用户', 'user', '普通用户', 1);

INSERT INTO `sys_permission` (`perm_name`, `perm_code`, `description`, `status`) VALUES 
('用户管理', 'user:manage', '用户管理权限', 1),
('角色管理', 'role:manage', '角色管理权限', 1),
('权限管理', 'perm:manage', '权限管理权限', 1),
('宝物管理', 'treasure:manage', '宝物管理权限', 1),
('订单管理', 'order:manage', '订单管理权限', 1);

INSERT INTO `sys_menu` (`parent_id`, `menu_name`, `path`, `component`, `icon`, `sort_order`, `status`) VALUES 
(0, '法器管理', '/magic-tool', 'magic-tool/index', 'icon-tool', 1, 1),
(0, '兵器管理', '/weapon', 'weapon/index', 'icon-sword', 2, 1),
(0, '丹药管理', '/pill', 'pill/index', 'icon-pill', 3, 1),
(0, '灵材管理', '/spirit-material', 'spirit-material/index', 'icon-leaf', 4, 1),
(0, '功法管理', '/skill', 'skill/index', 'icon-book', 5, 1),
(0, '灵石管理', '/spirit-stone', 'spirit-stone/index', 'icon-gem', 6, 1),
(0, '系统管理', '/system', '', 'icon-setting', 7, 1),
(7, '用户管理', '/system/user', 'system/user/index', 'icon-user', 1, 1),
(7, '角色管理', '/system/role', 'system/role/index', 'icon-role', 2, 1),
(7, '权限管理', '/system/permission', 'system/permission/index', 'icon-key', 3, 1),
(0, '订单管理', '/order', 'order/index', 'icon-order', 8, 1),
(0, '宝物管理', '/treasure', 'treasure/index', 'icon-treasure', 9, 1);
