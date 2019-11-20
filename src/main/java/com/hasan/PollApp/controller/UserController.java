package com.hasan.PollApp.controller;

import com.hasan.PollApp.model.dao.UserEntity;
import com.hasan.PollApp.model.dto.TitleDto;
import com.hasan.PollApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> get(@PathVariable ("userId") Long id){
        return ResponseEntity.ok(userService.get(id));
    }

    @GetMapping
    public ResponseEntity<?> getByEmail(@RequestParam String email) { return ResponseEntity.ok(userService.getByEmail(email)); }

    @PostMapping("/{userId}/titles")
    public ResponseEntity<?> addTitle(@PathVariable ("userId") Long id, @RequestBody TitleDto titleDto){
        userService.addTitle(id, titleDto.getTitle());
        return ResponseEntity.ok(titleDto);
    }

    @DeleteMapping("/{userId}/titles")
    public ResponseEntity<?> removeTitle(@PathVariable ("userId") Long id, @RequestBody TitleDto titleDto){
        userService.removeTitle(id, titleDto.getTitle());
        return ResponseEntity.ok(titleDto);
    }

}
