package com.hasan.PollApp.controller.user;

import com.hasan.PollApp.model.dto.user.TitleDto;
import com.hasan.PollApp.service.user.UserService;
import com.hasan.PollApp.util.Operation;
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
        return Operation.getOperationResult(userService.get(id));
    }

    @GetMapping
    public ResponseEntity<?> getByEmail(@RequestParam String email) { return Operation.getOperationResult(userService.getByEmail(email)); }

    @PostMapping("/{userId}/titles")
    public ResponseEntity<?> addTitle(@PathVariable ("userId") Long id, @RequestBody TitleDto titleDto){
        return Operation.getOperationResult(userService.addTitle(id, titleDto.getTitle()));
    }

    @DeleteMapping("/{userId}/titles")
    public ResponseEntity<?> removeTitle(@PathVariable ("userId") Long id, @RequestBody TitleDto titleDto){
        return Operation.getOperationResult(userService.removeTitle(id, titleDto.getTitle()));
    }

}
