package com.hasan.PollApp.controller;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.CompanyDto;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.service.CompanyService;
import com.hasan.PollApp.service.PollService;
import org.apache.tomcat.jni.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private PollService pollService;

    @GetMapping
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(companyService.list());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody CompanyDto companyDto){
        companyService.add(companyDto);
        return ResponseEntity.ok(companyDto);
    }

    @GetMapping("/{companyName}/polls")
    public ResponseEntity<?> listPolls(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(companyService.listPolls(companyName));
    }

    @PostMapping("/{companyName}/polls")
    public ResponseEntity<?> addPoll(@PathVariable("companyName") String companyName, @RequestBody PollDto pollDto){
        PollEntity poll = pollService.add(pollDto);
        companyService.addPoll(companyName, poll);
        return ResponseEntity.ok(pollDto);
    }

    @GetMapping("/{companyName}/polls/{pollIndex}")
    public ResponseEntity<?> getPoll(@PathVariable("companyName") String companyName, @PathVariable("pollIndex") int pollIndex){
        PollEntity poll = companyService.getPoll(companyName, pollIndex);
        return ResponseEntity.ok(poll);
    }
}
