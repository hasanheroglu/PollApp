package com.softeng.votit.service.user;

import com.softeng.votit.model.dto.user.UserDto;
import com.softeng.votit.util.Operation;


public interface UserService{
    Operation get(Long id);
    Operation getByEmail(String email);
    Operation getVoterPolls(Long id);
    Operation getOwnedPolls(Long id);
    Operation add(String companyName, UserDto userDto);
    Operation remove(String companyName, Long id);
    Operation addTitle(Long id, Long titleId);
    Operation removeTitle(Long id, Long titleId);
    Operation addRole(Long id, String role);
    Operation removeRole(Long id, String role);
}
