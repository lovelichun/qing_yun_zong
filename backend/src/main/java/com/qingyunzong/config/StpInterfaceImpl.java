package com.qingyunzong.config;

import cn.dev33.satoken.stp.StpInterface;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.service.SystemService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SystemService systemService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Long userId = Long.parseLong(String.valueOf(loginId));
        List<Role> roles = systemService.getUserRoles(userId);
        
        return roles.stream()
                .flatMap(role -> systemService.getRolePermissions(role.getId()).stream())
                .map(Permission::getPermCode)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long userId = Long.parseLong(String.valueOf(loginId));
        List<Role> roles = systemService.getUserRoles(userId);
        
        return roles.stream()
                .map(Role::getRoleCode)
                .collect(Collectors.toList());
    }
}