package com.qingyunzong.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qingyunzong.dto.LoginDTO;
import com.qingyunzong.entity.Permission;
import com.qingyunzong.entity.Role;
import com.qingyunzong.entity.User;
import com.qingyunzong.mapper.UserMapper;
import com.qingyunzong.service.LoginService;
import com.qingyunzong.service.SystemService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource
    private SystemService systemService;

    @Override
    public Map<String, Object> login(LoginDTO loginDTO) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, loginDTO.getUsername()));

        if (user == null || !user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已禁用");
        }

        StpUtil.login(user.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        
        List<Role> roles = systemService.getUserRoles(user.getId());
        result.put("roles", roles.stream().map(Role::getRoleCode).collect(Collectors.toList()));
        
        List<Permission> permissions = roles.stream()
                .flatMap(role -> systemService.getRolePermissions(role.getId()).stream())
                .distinct()
                .collect(Collectors.toList());
        result.put("permissions", permissions.stream().map(Permission::getPermCode).collect(Collectors.toList()));
        
        return result;
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public String getCaptcha() {
        String captchaText = captchaProducer.createText();
        
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(captchaImage, "png", outputStream);
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("生成验证码失败", e);
        }
    }
}
