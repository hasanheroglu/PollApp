package com.hasan.PollApp.service.impl.poll;

import com.hasan.PollApp.model.dao.poll.OptionEntity;
import com.hasan.PollApp.model.dao.poll.PollEntity;
import com.hasan.PollApp.model.dao.poll.UserVoteEntity;
import com.hasan.PollApp.model.dao.user.AccessibilityEntity;
import com.hasan.PollApp.model.dao.user.CompanyEntity;
import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.dto.poll.PollDto;
import com.hasan.PollApp.model.dto.poll.PollUpdateDto;
import com.hasan.PollApp.model.dto.poll.VoteDto;
import com.hasan.PollApp.model.repo.poll.UserVoteRepository;
import com.hasan.PollApp.model.repo.user.CompanyRepository;
import com.hasan.PollApp.model.repo.poll.OptionRepository;
import com.hasan.PollApp.model.repo.poll.PollRepository;
import com.hasan.PollApp.model.repo.user.UserRepository;
import com.hasan.PollApp.service.poll.PollService;
import com.hasan.PollApp.util.Operation;
import com.hasan.PollApp.util.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserVoteRepository userVoteRepository;
    @Autowired
    private EmailServiceImpl emailService;

    //MISC.

    @Override
    public Operation getAll(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null){ return new Operation<>(OperationStatus.COMPANY_NOT_FOUND); }

        List<PollEntity> polls = company.getPolls();

        return new Operation<>(OperationStatus.POLL_FOUND, polls);
    }
    @Override
    public Operation get(Long id) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()) {return new Operation<>(OperationStatus.POLL_NOT_FOUND);}

        PollEntity poll = optionalPoll.get();

        return new Operation<>(OperationStatus.POLL_FOUND, poll);
    }

    //MANIPULATION OPERATIONS

    @Override
    public Operation add(String companyName, PollDto pollDto) {
        PollEntity poll = new PollEntity(pollDto);
        pollRepository.save(poll);
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null){ return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        addOptions(poll, pollDto);
        addVoters(poll, pollDto);
        addCompany(poll, company);
        sendNotifications(poll);

        return new Operation<>(OperationStatus.POLL_ADDED, poll);
    }
    private void addOptions(PollEntity poll, PollDto pollDto){
        for (String option: pollDto.getOptions()) {
            OptionEntity newOption = new OptionEntity(option);
            newOption.setPoll(poll);
            poll.getOptions().add(newOption);
        }

        pollRepository.save(poll);
    }
    private void addVoters(PollEntity poll, PollDto pollDto){
        for(Long voterId: pollDto.getVoterIdList()){
            Optional<UserEntity> optionalUser = userRepository.findById(voterId);

            if(!optionalUser.isPresent()) {continue;}

            UserEntity user = optionalUser.get();

            UserVoteEntity userVote = new UserVoteEntity();
            userVoteRepository.save(userVote);

            userVote.setPoll(poll);
            userVote.setUser(user);

            poll.getVotes().add(userVote);
            user.getVotes().add(userVote);


            poll.getUsers().add(user);
            user.getPolls().add(poll);

            userRepository.save(user);
        }

        pollRepository.save(poll);
    }
    private void addCompany(PollEntity poll, CompanyEntity company){
        poll.setCompany(company);
        company.getPolls().add(poll);
        companyRepository.save(company);
    }
    private void sendNotification(AccessibilityEntity accessibilityOption, String subject, String message){
        if(accessibilityOption.getType().equals("email")){
            emailService.sendSimpleMessage(accessibilityOption.getContent(), subject,  message);
        }
    }
    private void sendNotifications(PollEntity poll){
        for(UserEntity user: poll.getUsers()){
            for(AccessibilityEntity accessibilityOption: user.getAccessibilityOptions()){
                sendNotification(accessibilityOption, "New poll: " + poll.getTitle()  + " Added!", "Dear " + user.getName() + " " + user.getSurname()  + ",\nNew poll added and you are invited to vote. " +
                        "Using accessibility option now!\n Start Date: " + poll.getStartDate() + "\nEnd Date: " + poll.getEndDate() + "\nPlease do not forget to vote before the end date. https://localhost:3000/companies/"+ poll.getCompany().getName() +"/polls/" + poll.getId()
                );
            }
        }
    }

    @Override
    public Operation update(Long id, PollUpdateDto pollUpdateDto) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()){ return new Operation(OperationStatus.POLL_NOT_FOUND); }

        PollEntity poll = optionalPoll.get();

        poll.setEndDate(pollUpdateDto.getEndDate());
        poll = pollRepository.save(poll);

        return new Operation<>(OperationStatus.POLL_UPDATED, poll);
    }

    @Override
    public Operation remove(Long id) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()){ return new Operation(OperationStatus.POLL_NOT_FOUND); }

        PollEntity poll = optionalPoll.get();

        pollRepository.delete(poll);
        return new Operation(OperationStatus.POLL_DELETED, poll);
    }

    //VOTE OPERATIONS

    @Override
    public Operation vote(VoteDto voteDto) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(voteDto.getPollId());
        if(!optionalPoll.isPresent()) {return new Operation(OperationStatus.POLL_NOT_FOUND);}

        PollEntity poll = optionalPoll.get();
        return applyVote(poll, voteDto);
    }

    private Operation applyVote(PollEntity poll, VoteDto voteDto){
        int index=0;

        Optional<UserEntity> optionalUser = userRepository.findById(voteDto.getVoterId());
        if(!optionalUser.isPresent()){return new Operation(OperationStatus.USER_NOT_FOUND);}

        UserEntity user = optionalUser.get();

        UserVoteEntity userVote = findUserVote(poll, user);
        if(userVote == null){ return new Operation(OperationStatus.NOT_VOTED); }
        if(userVote.isVoted()){ return new Operation(OperationStatus.NOT_VOTED); }
        if(voteDto.getOptionIds().isEmpty()){ return new Operation(OperationStatus.NOT_VOTED); }

        for(Long optionId: voteDto.getOptionIds()){
            Optional<OptionEntity> optionalOption = optionRepository.findById(optionId);
            if(!optionalOption.isPresent()) {return new Operation(OperationStatus.OPTION_NOT_FOUND);}

            OptionEntity option = optionalOption.get();

            poll.setEntryCount(poll.getEntryCount() + voteDto.getVotePoints().get(index));
            option.setCount(option.getCount() + voteDto.getVotePoints().get(index));

            optionRepository.save(option);
            pollRepository.save(poll);

            index++;
        }

        userVote.setVoted(true);
        userVoteRepository.save(userVote);

        return new Operation<>(OperationStatus.VOTED, voteDto);
    }

    private UserVoteEntity findUserVote(PollEntity poll, UserEntity user){

        for(UserVoteEntity userVote: poll.getVotes()){
            if(userVote.getUser().getId().equals(user.getId())){
                return userVote;
            }
        }

        return null;
    }
}
