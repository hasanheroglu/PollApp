package com.hasan.PollApp.service.poll;

import com.hasan.PollApp.model.dto.poll.PollDto;
import com.hasan.PollApp.model.dto.poll.PollUpdateDto;
import com.hasan.PollApp.model.dto.poll.VoteDto;
import com.hasan.PollApp.util.Operation;

public interface PollService {
    Operation getAll(String companyName);
    Operation get(Long id);
    Operation add(String companyName, PollDto pollDto);
    Operation update(Long id, PollUpdateDto pollUpdateDto);
    Operation vote(VoteDto voteDto);
}
