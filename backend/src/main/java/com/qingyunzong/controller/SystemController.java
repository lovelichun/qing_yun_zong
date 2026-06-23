package com.qingyunzong.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.common.Result;
import com.qingyunzong.entity.Menu;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.entity.User;
import com.qingyunzong.service.SystemService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Resource
    private SystemService systemService;

    @SaCheckPermission("user:manage")
    @GetMapping("/user/list")
    public Result<IPage<User>> listUsers(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> page = new Page<>(pageNum, pageSize);
        IPage<User> result = systemService.listUsers(page);
        return Result.success(result);
    }

    @SaCheckPermission("user:manage")
    @GetMapping("/user/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        return Result.success(systemService.getUserById(id));
    }

    @SaCheckPermission("user:manage")
    @PostMapping("/user")
    public Result<Void> saveUser(@RequestBody User user) {
        systemService.saveUser(user);
        return Result.success(null);
    }

    @SaCheckPermission("user:manage")
    @PutMapping("/user")
    public Result<Void> updateUser(@RequestBody User user) {
        systemService.updateUser(user);
        return Result.success(null);
    }

    @SaCheckPermission("user:manage")
    @DeleteMapping("/user/{id}")
    public Result<Void> deleteUserById(@PathVariable Long id) {
        systemService.deleteUserById(id);
        return Result.success(null);
    }

    @SaCheckPermission("role:manage")
    @GetMapping("/role/list")
    public Result<IPage<Role>> listRoles(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        IPage<Role> result = systemService.listRoles(page);
        return Result.success(result);
    }

    @SaCheckPermission("role:manage")
    @GetMapping("/role/{id}")
    public Result<Role> getRoleById(@PathVariable Long id) {
        return Result.success(systemService.getRoleById(id));
    }

    @SaCheckPermission("role:manage")
    @PostMapping("/role")
    public Result<Void> saveRole(@RequestBody Role role) {
        systemService.saveRole(role);
        return Result.success(null);
    }

    @SaCheckPermission("role:manage")
    @PutMapping("/role")
    public Result<Void> updateRole(@RequestBody Role role) {
        systemService.updateRole(role);
        return Result.success(null);
    }

    @SaCheckPermission("role:manage")
    @DeleteMapping("/role/{id}")
    public Result<Void> deleteRoleById(@PathVariable Long id) {
        systemService.deleteRoleById(id);
        return Result.success(null);
    }

    @SaCheckPermission("perm:manage")
    @GetMapping("/permission/list")
    public Result<IPage<Permission>> listPermissions(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Permission> page = new Page<>(pageNum, pageSize);
        IPage<Permission> result = systemService.listPermissions(page);
        return Result.success(result);
    }

    @SaCheckPermission("perm:manage")
    @GetMapping("/permission/{id}")
    public Result<Permission> getPermissionById(@PathVariable Long id) {
        return Result.success(systemService.getPermissionById(id));
    }

    @SaCheckPermission("perm:manage")
    @PostMapping("/permission")
    public Result<Void> savePermission(@RequestBody Permission permission) {
        systemService.savePermission(permission);
        return Result.success(null);
    }

    @SaCheckPermission("perm:manage")
    @PutMapping("/permission")
    public Result<Void> updatePermission(@RequestBody Permission permission) {
        systemService.updatePermission(permission);
        return Result.success(null);
    }

    @SaCheckPermission("perm:manage")
    @DeleteMapping("/permission/{id}")
    public Result<Void> deletePermissionById(@PathVariable Long id) {
        systemService.deletePermissionById(id);
        return Result.success(null);
    }

    @SaCheckPermission("user:manage")
    @PostMapping("/user/{userId}/roles")
    public Result<Void> assignRolesToUser(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        systemService.assignRolesToUser(userId, roleIds);
        return Result.success(null);
    }

    @SaCheckPermission("user:manage")
    @GetMapping("/user/{userId}/roles")
    public Result<List<Role>> getUserRoles(@PathVariable Long userId) {
        return Result.success(systemService.getUserRoles(userId));
    }

    @SaCheckPermission("role:manage")
    @PostMapping("/role/{roleId}/permissions")
    public Result<Void> assignPermissionsToRole(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        systemService.assignPermissionsToRole(roleId, permissionIds);
        return Result.success(null);
    }

    @SaCheckPermission("role:manage")
    @GetMapping("/role/{roleId}/permissions")
    public Result<List<Permission>> getRolePermissions(@PathVariable Long roleId) {
        return Result.success(systemService.getRolePermissions(roleId));
    }

    @SaCheckPermission("role:manage")
    @PostMapping("/role/{roleId}/menus")
    public Result<Void> assignMenusToRole(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        systemService.assignMenusToRole(roleId, menuIds);
        return Result.success(null);
    }

    @SaCheckPermission("role:manage")
    @GetMapping("/role/{roleId}/menus")
    public Result<List<Menu>> getRoleMenus(@PathVariable Long roleId) {
        return Result.success(systemService.getRoleMenus(roleId));
    }

    @GetMapping("/user/menus")
    public Result<List<Menu>> getUserMenus() {
        Long userId = Long.parseLong(cn.dev33.satoken.stp.StpUtil.getLoginIdAsString());
        return Result.success(systemService.getUserMenus(userId));
    }
}