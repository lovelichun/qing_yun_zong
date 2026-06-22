package com.qingyunzong.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.entity.User;
import com.qingyunzong.mapper.PermissionMapper;
import com.qingyunzong.mapper.RoleMapper;
import com.qingyunzong.mapper.UserMapper;
import com.qingyunzong.service.SystemService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

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
}
