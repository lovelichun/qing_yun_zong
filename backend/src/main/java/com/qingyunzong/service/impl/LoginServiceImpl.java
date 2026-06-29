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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private DefaultKaptcha captchaProducer;

    @Resource
    private SystemService systemService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String CAPTCHA_PREFIX = "captcha:";
    private static final long CAPTCHA_EXPIRE_SECONDS = 60;

    @Override
    public Map<String, Object> login(LoginDTO loginDTO) {
        String captchaKey = loginDTO.getCaptchaKey();
        String captcha = loginDTO.getCaptcha();

        String key = CAPTCHA_PREFIX + captchaKey;
        String storedCaptcha = stringRedisTemplate.opsForValue().get(key);
        
        if (storedCaptcha == null) {
            throw new RuntimeException("验证码已过期");
        }
        
        if (!storedCaptcha.equalsIgnoreCase(captcha)) {
            throw new RuntimeException("验证码错误");
        }
        
        stringRedisTemplate.delete(key);

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
    public Map<String, Object> getCaptcha() {
        String captchaText = captchaProducer.createText();
        
        String captchaKey = UUID.randomUUID().toString().replace("-", "");
        
        String key = CAPTCHA_PREFIX + captchaKey;
        stringRedisTemplate.opsForValue().set(key, captchaText, CAPTCHA_EXPIRE_SECONDS, TimeUnit.SECONDS);
        
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);
        
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(captchaImage, "png", outputStream);
            String imageBase64 = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
            
            Map<String, Object> result = new HashMap<>();
            result.put("captchaKey", captchaKey);
            result.put("image", imageBase64);
            return result;
        } catch (IOException e) {
            throw new RuntimeException("生成验证码失败", e);
        }
    }
}
