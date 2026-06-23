package com.qingyunzong.service;

import com.qingyunzong.dto.LoginDTO;

import java.util.Map;

public interface LoginService {

    Map<String, Object> login(LoginDTO loginDTO);

    void logout();

    String getCaptcha();
}
