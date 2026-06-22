package com.qingyunzong.service;

import com.qingyunzong.dto.LoginDTO;
import com.qingyunzong.entity.User;

public interface LoginService {

    User login(LoginDTO loginDTO);

    void logout();

    String getCaptcha();
}
