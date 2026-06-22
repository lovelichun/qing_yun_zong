# 青云宗后台管理系统

## 项目简介

青云宗后台管理系统是一个基于 Spring Boot 3.5 + Element Plus 的全栈管理系统，用于管理青云宗的宝物资源。

## 技术栈

### 后端技术
- Spring Boot 3.5.0
- MyBatis Plus 3.5.5
- Sa-Token 1.37.0
- MySQL 8.0+

### 前端技术
- Vue 3.5.12
- Element Plus 2.16.5
- Vue Router 4.5.0
- Vite 6.5.0

## 功能模块

### 登录功能
- 用户名密码登录
- 图形验证码

### 数据字典管理
- 宝物类别：法器、兵器、丹药、灵材、功法、灵石、符箓
- 宝物级别：凡级、灵级、宝级、仙级、神级
- 宝物品阶：下品、中品、上品、极品
- 宝物名称：青钢剑、灵木弓、护心镜、轻羽扇、传音玉牌、寻灵石盘、聚气丹、止血丹、符纸

### 菜单管理
- 法器管理、兵器管理、丹药管理、灵材管理、功法管理、灵石管理
- 用户管理、角色管理、权限管理
- 订单管理、宝物管理

### 宝物管理
- 法器管理、兵器管理、丹药管理、灵材管理、功法管理、灵石管理、宝物管理
- 支持新增、编辑、删除、分页查询

### 订单管理
- 订单的增删改查

### 系统管理
- 用户管理、角色管理、权限管理

## 项目结构

```
treasure_pavilion/
├── backend/                    # 后端项目
│   ├── src/main/java/com/qingyunzong/
│   │   ├── TreasurePavilionApplication.java   # 启动类
│   │   ├── common/            # 公共模块
│   │   │   ├── Result.java    # 统一响应结果
│   │   │   └── DataDict.java  # 数据字典
│   │   ├── config/            # 配置类
│   │   │   ├── SaTokenConfig.java
│   │   │   ├── WebConfig.java
│   │   │   ├── KaptchaConfig.java
│   │   │   └── MyBatisPlusConfig.java
│   │   ├── controller/        # 控制器
│   │   ├── service/           # 服务层
│   │   ├── mapper/            # 数据访问层
│   │   ├── entity/            # 实体类
│   │   └── dto/               # 数据传输对象
│   ├── src/main/resources/
│   │   ├── application.yml    # 应用配置
│   │   └── schema.sql         # 数据库初始化脚本
│   └── pom.xml
├── frontend/                   # 前端项目
│   ├── src/
│   │   ├── api/               # API接口
│   │   ├── utils/             # 工具类
│   │   ├── router/            # 路由配置
│   │   ├── views/             # 页面组件
│   │   │   ├── login/         # 登录页面
│   │   │   ├── treasure/      # 宝物管理
│   │   │   ├── magic-tool/    # 法器管理
│   │   │   ├── weapon/        # 兵器管理
│   │   │   ├── pill/          # 丹药管理
│   │   │   ├── spirit-material/ # 灵材管理
│   │   │   ├── skill/         # 功法管理
│   │   │   ├── spirit-stone/  # 灵石管理
│   │   │   ├── order/         # 订单管理
│   │   │   ├── system/        # 系统管理
│   │   │   │   ├── user/      # 用户管理
│   │   │   │   ├── role/      # 角色管理
│   │   │   │   └── permission/ # 权限管理
│   │   │   ├── menu/          # 菜单管理
│   │   │   └── dict/          # 数据字典
│   │   ├── layout/            # 布局组件
│   │   ├── App.vue            # 根组件
│   │   ├── main.js            # 入口文件
│   │   └── style.css          # 全局样式
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
└── README.md
```

## 快速开始

### 环境要求
- JDK 21+
- Node.js 18+
- MySQL 8.0+

### 后端启动

1. 创建数据库
```sql
CREATE DATABASE qingyunzong CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 修改配置文件 `backend/src/main/resources/application.yml`
```yaml
spring:
  datasource:
    username: root
    password: your_password
```

3. 运行数据库初始化脚本 `backend/src/main/resources/schema.sql`

4. 启动后端服务
```bash
cd backend
mvn spring-boot:run
```

### 前端启动

1. 安装依赖
```bash
cd frontend
npm install
```

2. 启动开发服务器
```bash
npm run dev
```

3. 访问地址：http://localhost:5173

### 登录信息

- 用户名：admin
- 密码：123456

## 接口说明

### 登录接口
- POST /login - 用户登录
- GET /captcha - 获取验证码
- POST /logout - 用户退出

### 宝物管理接口
- GET /treasure/list - 分页查询宝物
- GET /treasure/{id} - 查询单个宝物
- POST /treasure - 添加宝物
- PUT /treasure - 修改宝物
- DELETE /treasure/{id} - 删除宝物
- GET /treasure/dict - 获取数据字典

### 订单管理接口
- GET /order/list - 分页查询订单
- GET /order/{id} - 查询单个订单
- POST /order - 添加订单
- PUT /order - 修改订单
- DELETE /order/{id} - 删除订单

### 系统管理接口
- GET /system/user/list - 分页查询用户
- POST /system/user - 添加用户
- PUT /system/user - 修改用户
- DELETE /system/user/{id} - 删除用户

- GET /system/role/list - 分页查询角色
- POST /system/role - 添加角色
- PUT /system/role - 修改角色
- DELETE /system/role/{id} - 删除角色

- GET /system/permission/list - 分页查询权限
- POST /system/permission - 添加权限
- PUT /system/permission - 修改权限
- DELETE /system/permission/{id} - 删除权限

### 菜单管理接口
- GET /menu/list - 分页查询菜单
- POST /menu - 添加菜单
- PUT /menu - 修改菜单
- DELETE /menu/{id} - 删除菜单
