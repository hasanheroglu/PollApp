package com.softeng.votit.service.user;

import com.softeng.votit.model.dto.user.LoginDto;

public interface LoginService {
    void login(LoginDto loginDto);
}