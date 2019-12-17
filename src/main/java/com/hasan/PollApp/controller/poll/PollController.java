package com.hasan.PollApp.controller.poll;

import com.hasan.PollApp.model.dto.poll.PollDto;
import com.hasan.PollApp.model.dto.poll.PollUpdateDto;
import com.hasan.PollApp.model.dto.poll.VoteDto;
import com.hasan.PollApp.service.poll.PollService;
import com.hasan.PollApp.util.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/companies/{companyName}")
public class PollController {
    @Autowired
    private PollService pollService;

    @GetMapping("/polls")
    @PreAuthorize("hasAnyAuthority('ROLE_SYSTEM_ADMIN', 'ROLE_COMPANY_ADMIN')")
    public ResponseEntity<?> list(@PathVariable("companyName") String companyName){
        return Operation.getOperationResult(pollService.getAll(companyName));
    }

    @PostMapping("/polls")
    @PreAuthorize("hasAuthority('ROLE_POLL_OWNER')")
    public ResponseEntity<?> add(@PathVariable("companyName") String companyName, @RequestBody PollDto pollDto){
        return Operation.getOperationResult(pollService.add(companyName, pollDto));
    }

    @PutMapping("/polls/{pollId}")
    @PreAuthorize("hasAuthority('ROLE_POLL_OWNER')")
    public ResponseEntity<?> update(@PathVariable("companyName") String companyName, @PathVariable("pollId") Long pollId, @RequestBody PollUpdateDto pollUpdateDto){
        return Operation.getOperationResult(pollService.update(pollId, pollUpdateDto));
    }

    @DeleteMapping("/polls/{pollId}")
    @PreAuthorize("hasAuthority('ROLE_POLL_OWNER')")
    public ResponseEntity<?> remove(@PathVariable("companyName") String companyName, @PathVariable("pollId") Long pollId) {
        return Operation.getOperationResult(pollService.remove(pollId));
    }

    @GetMapping("/polls/{pollId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> get(@PathVariable("companyName") String companyName, @PathVariable("pollId") Long pollId){
        return Operation.getOperationResult(pollService.get(pollId));
    }

    @PostMapping("/polls/{pollId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> vote(@PathVariable("companyName") String companyName, @PathVariable("pollId") Long pollId, @RequestBody VoteDto voteDto){
        return Operation.getOperationResult(pollService.vote(voteDto));
    }


}
