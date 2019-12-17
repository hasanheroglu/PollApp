package com.softeng.votit.service.poll;

import com.softeng.votit.model.dto.poll.PollDto;
import com.softeng.votit.model.dto.poll.PollUpdateDto;
import com.softeng.votit.model.dto.poll.VoteDto;
import com.softeng.votit.util.Operation;

public interface PollService {
    Operation getAll(String companyName);
    Operation get(Long id);
    Operation add(String companyName, PollDto pollDto);
    Operation update(Long id, PollUpdateDto pollUpdateDto);
    Operation remove(Long id);
    Operation vote(VoteDto voteDto);
}
