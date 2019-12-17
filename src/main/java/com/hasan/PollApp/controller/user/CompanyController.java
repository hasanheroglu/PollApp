package com.hasan.PollApp.controller.user;

import com.hasan.PollApp.model.dto.user.CompanyDto;
import com.hasan.PollApp.model.dto.user.CompanyUpdateDto;
import com.hasan.PollApp.model.dto.user.TitleDto;
import com.hasan.PollApp.model.dto.user.UserDto;
import com.hasan.PollApp.service.user.CompanyService;
import com.hasan.PollApp.service.user.UserService;
import com.hasan.PollApp.util.Operation;
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
    private UserService userService;

    //COMPANY APPLICATIONS

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<?> list(){
        return Operation.getOperationResult(companyService.list());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<?> add(@RequestBody CompanyDto companyDto){
        return Operation.getOperationResult(companyService.add(companyDto));
    }

    @PutMapping("/{companyName}")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> update(@PathVariable("companyName") String companyName, @RequestBody CompanyUpdateDto companyUpdateDto){

        return Operation.getOperationResult(companyService.update(companyName, companyUpdateDto));
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<?> get(@PathVariable("companyName") String companyName){
        return Operation.getOperationResult(companyService.get(companyName));
    }

    @DeleteMapping("/{companyName}")
    @PreAuthorize("hasAuthority('ROLE_SYSTEM_ADMIN')")
    public ResponseEntity<?> remove(@PathVariable("companyName") String companyName){
        return Operation.getOperationResult(companyService.remove(companyName));
    }

    //USER APPLICATIONS

    @GetMapping("/{companyName}/users")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_ADMIN', 'ROLE_POLL_OWNER')")
    public ResponseEntity<?> listUsers(@PathVariable("companyName") String companyName){
        return Operation.getOperationResult(companyService.listUsers(companyName));
    }

    @GetMapping("/{companyName}/{title}/users")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_ADMIN', 'ROLE_POLL_OWNER')")
    public ResponseEntity<?> listUsersByTitle(@PathVariable("companyName") String companyName, @PathVariable("title") String title){
        return Operation.getOperationResult(companyService.listUsersByTitle(companyName, title));
    }


    @PostMapping("/{companyName}/users")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> addUser(@PathVariable("companyName") String companyName, @RequestBody UserDto userDto){
        return Operation.getOperationResult(userService.add(companyName, userDto));
    }

    @DeleteMapping("/{companyName}/users/{userId}")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> removeUser(@PathVariable("companyName") String companyName, @PathVariable ("userId") Long userId){
        return Operation.getOperationResult(userService.remove(companyName, userId));
    }

    //TITLE APPLICATIONS

    @GetMapping("/{companyName}/titles")
    @PreAuthorize("hasAnyAuthority('ROLE_COMPANY_ADMIN', 'ROLE_POLL_OWNER')")
    public ResponseEntity<?> listTitles(@PathVariable("companyName") String companyName){
        return Operation.getOperationResult(companyService.listTitles(companyName));
    }

    @PostMapping("/{companyName}/titles")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> addTitle(@PathVariable("companyName") String companyName, @RequestBody TitleDto titleDto){
        return Operation.getOperationResult(companyService.addTitle(companyName, titleDto));
    }

    @DeleteMapping("/{companyName}/titles/{titleId}")
    @PreAuthorize("hasAuthority('ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> removeTitle(@PathVariable("companyName") String companyName, @PathVariable("titleId") Long titleId){
        return Operation.getOperationResult(companyService.removeTitle(companyName, titleId));
    }
}
