package com.hasan.PollApp.controller.user;

import com.hasan.PollApp.model.dto.user.LoginDto;
import com.hasan.PollApp.service.impl.user.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginServiceImpl loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        loginService.login(loginDto);
        return ResponseEntity.ok("hey!");
    }
}