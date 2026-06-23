package com.qingyunzong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.entity.Menu;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.entity.RoleMenu;
import com.qingyunzong.entity.RolePermission;
import com.qingyunzong.entity.User;
import com.qingyunzong.entity.UserRole;
import com.qingyunzong.mapper.MenuMapper;
import com.qingyunzong.mapper.PermissionMapper;
import com.qingyunzong.mapper.RoleMapper;
import com.qingyunzong.mapper.RoleMenuMapper;
import com.qingyunzong.mapper.RolePermissionMapper;
import com.qingyunzong.mapper.UserMapper;
import com.qingyunzong.mapper.UserRoleMapper;
import com.qingyunzong.service.SystemService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public IPage<User> listUsers(Page<User> page) {
        return userMapper.selectPage(page, null);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public void saveUser(User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public IPage<Role> listRoles(Page<Role> page) {
        return roleMapper.selectPage(page, null);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public void saveRole(Role role) {
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        role.setUpdateTime(LocalDateTime.now());
        roleMapper.updateById(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleMapper.deleteById(id);
    }

    @Override
    public IPage<Permission> listPermissions(Page<Permission> page) {
        return permissionMapper.selectPage(page, null);
    }

    @Override
    public Permission getPermissionById(Long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public void savePermission(Permission permission) {
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.insert(permission);
    }

    @Override
    public void updatePermission(Permission permission) {
        permission.setUpdateTime(LocalDateTime.now());
        permissionMapper.updateById(permission);
    }

    @Override
    public void deletePermissionById(Long id) {
        permissionMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRolesToUser(Long userId, List<Long> roleIds) {
        // 删除用户原有的角色关联
        LambdaQueryWrapper<UserRole> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(UserRole::getUserId, userId);
        userRoleMapper.delete(deleteWrapper);

        // 添加新的角色关联
        if (roleIds != null && !roleIds.isEmpty()) {
            List<UserRole> userRoles = roleIds.stream().map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreateTime(LocalDateTime.now());
                return userRole;
            }).collect(Collectors.toList());
            userRoles.forEach(userRoleMapper::insert);
        }
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleMapper.selectList(wrapper);
        
        if (userRoles.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        
        return roleMapper.selectBatchIds(roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissionsToRole(Long roleId, List<Long> permissionIds) {
        // 删除角色原有的权限关联
        LambdaQueryWrapper<RolePermission> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(RolePermission::getRoleId, roleId);
        rolePermissionMapper.delete(deleteWrapper);

        // 添加新的权限关联
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<RolePermission> rolePermissions = permissionIds.stream().map(permissionId -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                rolePermission.setCreateTime(LocalDateTime.now());
                return rolePermission;
            }).collect(Collectors.toList());
            rolePermissions.forEach(rolePermissionMapper::insert);
        }
    }

    @Override
    public List<Permission> getRolePermissions(Long roleId) {
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);
        
        if (rolePermissions.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        
        return permissionMapper.selectBatchIds(permissionIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenusToRole(Long roleId, List<Long> menuIds) {
        LambdaQueryWrapper<RoleMenu> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(RoleMenu::getRoleId, roleId);
        roleMenuMapper.delete(deleteWrapper);

        if (menuIds != null && !menuIds.isEmpty()) {
            List<RoleMenu> roleMenus = menuIds.stream().map(menuId -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menuId);
                roleMenu.setCreateTime(LocalDateTime.now());
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenus.forEach(roleMenuMapper::insert);
        }
    }

    @Override
    public List<Menu> getRoleMenus(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        
        if (roleMenus.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Long> menuIds = roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());
        
        return menuMapper.selectBatchIds(menuIds);
    }

    @Override
    public List<Menu> getUserMenus(Long userId) {
        List<Role> roles = getUserRoles(userId);
        
        if (roles.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
        
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RoleMenu::getRoleId, roleIds);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        
        if (roleMenus.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Long> menuIds = roleMenus.stream()
                .map(RoleMenu::getMenuId)
                .distinct()
                .collect(Collectors.toList());
        
        return menuMapper.selectBatchIds(menuIds);
    }
}
