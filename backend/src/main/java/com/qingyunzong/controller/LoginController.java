package com.qingyunzong.controller;

import com.qingyunzong.common.Result;
import com.qingyunzong.dto.LoginDTO;
import com.qingyunzong.service.LoginService;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @SaIgnore
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        Map<String, Object> result = loginService.login(loginDTO);
        result.put("token", StpUtil.getTokenValue());
        return Result.success(result);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        loginService.logout();
        return Result.success(null);
    }
    @SaIgnore
    @GetMapping("/captcha")
    public Result<Map<String, Object>> captcha() {
        return Result.success(loginService.getCaptcha());
    }
}
