package com.qingyunzong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.entity.Menu;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.entity.User;

import java.util.List;

public interface SystemService {

    IPage<User> listUsers(Page<User> page);
    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(Long id);

    IPage<Role> listRoles(Page<Role> page);
    Role getRoleById(Long id);
    void saveRole(Role role);
    void updateRole(Role role);
    void deleteRoleById(Long id);

    IPage<Permission> listPermissions(Page<Permission> page);
    Permission getPermissionById(Long id);
    void savePermission(Permission permission);
    void updatePermission(Permission permission);
    void deletePermissionById(Long id);

    // 用户角色关联
    void assignRolesToUser(Long userId, List<Long> roleIds);
    List<Role> getUserRoles(Long userId);

    // 角色权限关联
    void assignPermissionsToRole(Long roleId, List<Long> permissionIds);
    List<Permission> getRolePermissions(Long roleId);

    // 角色菜单关联
    void assignMenusToRole(Long roleId, List<Long> menuIds);
    List<Menu> getRoleMenus(Long roleId);

    // 获取用户菜单（用于前端动态渲染）
    List<Menu> getUserMenus(Long userId);
}
