package com.hasan.PollApp.controller;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.*;
import com.hasan.PollApp.service.CompanyService;
import com.hasan.PollApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/companies")
public class CompanyController {




    @Autowired
    private CompanyService companyService;
    @Autowired
    private PollService pollService;

    //COMPANY APPLICATIONS

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(companyService.list());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<?> add(@RequestBody CompanyDto companyDto){
        companyService.add(companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping("/{companyName}")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN', 'ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> get(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.get(companyName));
    }

    @DeleteMapping("/{companyName}")
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<?> remove(@PathVariable("companyName") String companyName){
        companyService.remove(companyName);
        return ResponseEntity.ok(companyName);
    }

    //USER APPLICATIONS

    @GetMapping("/{companyName}/users")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_ADMIN', 'ROLE_POLL_OWNER')")
    public ResponseEntity<?> listUsers(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.listUsers(companyName));
    }

    @GetMapping("/{companyName}/{title}/users")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_ADMIN', 'ROLE_POLL_OWNER')")
    public ResponseEntity<?> listUsersByTitle(@PathVariable("companyName") String companyName, @PathVariable("title") String title){
        return ResponseEntity.ok(companyService.listUsersByTitle(companyName, title));
    }


    @PostMapping("/{companyName}/users")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> addUser(@PathVariable("companyName") String companyName, @RequestBody UserDto userDto){
        companyService.addUser(companyName, userDto);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{companyName}/users/{userId}")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> removeUser(@PathVariable("companyName") String companyName, @PathVariable ("userId") Long userId){
        companyService.removeUser(companyName, userId);
        return ResponseEntity.ok(companyName);
    }

    //TITLE APPLICATIONS

    @GetMapping("/{companyName}/titles")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_ADMIN', 'ROLE_POLL_OWNER')")
    public ResponseEntity<?> listTitles(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.listTitles(companyName));
    }

    @PostMapping("/{companyName}/titles")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> addTitle(@PathVariable("companyName") String companyName, @RequestBody TitleDto titleDto){
        companyService.addTitle(companyName, titleDto);
        return ResponseEntity.ok(titleDto);
    }
}
