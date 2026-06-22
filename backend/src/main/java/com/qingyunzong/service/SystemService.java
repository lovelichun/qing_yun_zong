package com.qingyunzong.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.entity.User;

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
}
