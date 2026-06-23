package com.qingyunzong.controller;

import com.qingyunzong.common.Result;
import com.qingyunzong.dto.LoginDTO;
import com.qingyunzong.entity.User;
import com.qingyunzong.service.LoginService;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.util.SaResult;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        User user = loginService.login(loginDTO);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("token", cn.dev33.satoken.stp.StpUtil.getTokenValue());
        SaResult.ok();
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        loginService.logout();
        return Result.success(null);
    }
    @SaIgnore
    @GetMapping("/captcha")
    public Result<String> captcha() {
        return Result.success(loginService.getCaptcha());
    }
}
