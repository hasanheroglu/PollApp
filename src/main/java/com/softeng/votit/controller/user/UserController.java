package com.softeng.votit.controller.user;

import com.softeng.votit.service.user.UserService;
import com.softeng.votit.util.Operation;
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

    @GetMapping("/{userId}/polls")
    public ResponseEntity<?> getPolls(@PathVariable("userId") Long id, @RequestParam boolean owned){
        if(owned){
            return Operation.getOperationResult(userService.getOwnedPolls(id));
        }

        return Operation.getOperationResult(userService.getVoterPolls(id));
    }

    @PostMapping("/{userId}/titles")
    public ResponseEntity<?> addTitle(@PathVariable ("userId") Long id, @RequestParam Long titleId){
        return Operation.getOperationResult(userService.addTitle(id, titleId));
    }

    @DeleteMapping("/{userId}/titles/{titleId}")
    public ResponseEntity<?> removeTitle(@PathVariable ("userId") Long id, @PathVariable Long titleId){
        return Operation.getOperationResult(userService.removeTitle(id, titleId));
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<?> addRole(@PathVariable ("userId") Long id, @RequestParam String role){
        return Operation.getOperationResult(userService.addRole(id, role));
    }

    @DeleteMapping("/{userId}/roles")
    public ResponseEntity<?> removeRole(@PathVariable ("userId") Long id, @RequestParam String role){
        return Operation.getOperationResult(userService.removeRole(id, role));
    }

}
