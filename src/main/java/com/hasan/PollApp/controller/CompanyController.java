package com.hasan.PollApp.controller;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.*;
import com.hasan.PollApp.service.CompanyService;
import com.hasan.PollApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(companyService.list());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CompanyDto companyDto){
        companyService.add(companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping("/{companyName}")
    public ResponseEntity<?> get(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.get(companyName));
    }

    @DeleteMapping("/{companyName}")
    public ResponseEntity<?> remove(@PathVariable("companyName") String companyName){
        companyService.remove(companyName);
        return ResponseEntity.ok(companyName);
    }

    //POLL APPLICATIONS
/*
    @GetMapping("/{companyName}/polls")
    public ResponseEntity<?> listPolls(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.listPolls(companyName));
    }

    @PostMapping("/{companyName}/polls")
    public ResponseEntity<?> addPoll(@PathVariable("companyName") String companyName, @RequestBody PollDto pollDto){
        PollEntity poll = pollService.add(companyName, pollDto);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/{companyName}/polls/{pollIndex}")
    public ResponseEntity<?> getPoll(@PathVariable("companyName") String companyName, @PathVariable("pollIndex") Long pollIndex){
        PollEntity poll = pollService.get(pollIndex);
        return ResponseEntity.ok(poll);
    }

    @PostMapping("/{companyName}/polls/{pollIndex}")
    public ResponseEntity<?> vote(@PathVariable("companyName") String companyName, @PathVariable("pollIndex") int pollIndex, @RequestBody VoteDto voteDto){
        pollService.vote(voteDto);
        return ResponseEntity.ok(voteDto);
    }

    @PostMapping("/{companyName}/polls/{pollIndex}/options")
    public ResponseEntity<?> addOption(@PathVariable("companyName") String companyName, @PathVariable("pollIndex") Long pollIndex, @RequestBody String optionBody){
        pollService.addOption(pollIndex, optionBody);
        return ResponseEntity.ok(optionBody);
    }
*/
    //USER APPLICATIONS

    @GetMapping("/{companyName}/users")
    public ResponseEntity<?> listUsers(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.listUsers(companyName));
    }

    @PostMapping("/{companyName}/users")
    public ResponseEntity<?> addUser(@PathVariable("companyName") String companyName, @RequestBody UserDto userDto){
        companyService.addUser(companyName, userDto);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{companyName}/users/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable("companyName") String companyName, @PathVariable ("userId") Long userId){
        companyService.removeUser(companyName, userId);
        return ResponseEntity.ok(companyName);
    }

    //TITLE APPLICATIONS

    @GetMapping("/{companyName}/titles")
    public ResponseEntity<?> listTitles(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.listTitles(companyName));
    }

    @PostMapping("/{companyName}/titles")
    public ResponseEntity<?> addTitle(@PathVariable("companyName") String companyName, @RequestBody TitleDto titleDto){
        companyService.addTitle(companyName, titleDto);
        return ResponseEntity.ok(titleDto);
    }
}
