package com.hasan.PollApp.service;

import com.hasan.PollApp.model.dao.UserEntity;
import com.hasan.PollApp.model.dto.UserDto;

public interface UserService{
    UserEntity get(Long id);
    UserEntity add(UserDto userDto);
    void addTitle(Long id, String name);
    void removeTitle(Long id, String name);
}
