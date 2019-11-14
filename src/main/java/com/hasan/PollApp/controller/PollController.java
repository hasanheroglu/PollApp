package com.hasan.PollApp.controller;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.model.dto.VoteDto;
import com.hasan.PollApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/companies/{companyName}")
public class PollController {
    @Autowired
    private PollService pollService;

    @GetMapping("/polls")
    public ResponseEntity<?> listPolls(@PathVariable("companyName") String companyName){
        return ResponseEntity.ok(pollService.getAll(companyName));
    }

    @PostMapping("/polls")
    public ResponseEntity<?> addPoll(@PathVariable("companyName") String companyName, @RequestBody PollDto pollDto){
        PollEntity poll = pollService.add(companyName, pollDto);
        return ResponseEntity.ok(poll);
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable("companyName") String companyName, @PathVariable("pollId") Long pollId){
        PollEntity poll = pollService.get(pollId);
        return ResponseEntity.ok(poll);
    }

    @PostMapping("/polls/{pollId}")
    public ResponseEntity<?> vote(@PathVariable("companyName") String companyName, @PathVariable("pollId") Long pollId, @RequestBody VoteDto voteDto){
        pollService.vote(voteDto);
        return ResponseEntity.ok(voteDto);
    }

    @PostMapping("/polls/{pollIndex}/options")
    public ResponseEntity<?> addOption(@PathVariable("companyName") String companyName, @PathVariable("pollIndex") Long pollId, @RequestBody String optionBody){
        pollService.addOption(pollId, optionBody);
        return ResponseEntity.ok(optionBody);
    }

}
