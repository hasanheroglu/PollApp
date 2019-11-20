package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.mail.EmailServiceImpl;
import com.hasan.PollApp.model.dao.*;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.model.dto.VoteDto;
import com.hasan.PollApp.model.repo.CompanyRepository;
import com.hasan.PollApp.model.repo.OptionRepository;
import com.hasan.PollApp.model.repo.PollRepository;
import com.hasan.PollApp.model.repo.UserRepository;
import com.hasan.PollApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
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
    private EmailServiceImpl emailService;

    @Override
    public Iterable<PollEntity> getAll(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);
        return company.getPolls();
    }

    @Override
    public PollEntity get(Long id) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()) {return null;}

        PollEntity poll = optionalPoll.get();
        return poll;
    }

    @Override
    public PollEntity add(String companyName, PollDto pollDto) {
        PollEntity poll = new PollEntity(pollDto);
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null){ return null; }

        for (String option: pollDto.getOptions()) {
            OptionEntity newOption = new OptionEntity(option);
            newOption.setPoll(poll);
            poll.getOptions().add(newOption);
        }

        for(Long voterId: pollDto.getVoterIdList()){
            Optional<UserEntity> optionalUser = userRepository.findById(voterId);

            if(!optionalUser.isPresent()) {continue;}

            UserEntity user = optionalUser.get();
            poll.getUsers().add(user);
            user.getPolls().add(poll);

            userRepository.save(user);
        }

        pollRepository.save(poll);

        poll.setCompany(company);

        company.getPolls().add(poll);

        companyRepository.save(company);

        for(UserEntity user: poll.getUsers()){
            for(AccessibilityEntity accessibilityOption: user.getAccessibilityOptions()){
                if(accessibilityOption.getType().equals("email")){
                    emailService.sendSimpleMessage(accessibilityOption.getContent(), "New Poll: " + poll.getTitle()  + " Added!", "Dear " + user.getName() + " " + user.getSurname()  + ",\nNew poll added and you are invited to vote. " +
                            "Using accessibility option now!\n Start Date: " + poll.getStartDate() + "\nEnd Date: " + poll.getEndDate() + "\nPlease do not forget to vote before the end date ^^. I hope this time dates are not null." );
                }
            }
        }

        return poll;
    }

    @Override
    public void addTitle(Long id, String title) {
        Optional<PollEntity> optionalPollEntity = pollRepository.findById(id);

        if(!optionalPollEntity.isPresent()){ return; }

        PollEntity pollEntity = optionalPollEntity.get();
        pollEntity.setTitle(title);
        pollRepository.save(pollEntity);
    }

    @Override
    public void addOption(Long id, String option) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(id);

        if(!optionalPoll.isPresent()) {return;}

        PollEntity poll = optionalPoll.get();
        OptionEntity newOption = new OptionEntity(option);

        newOption.setPoll(poll);
        poll.getOptions().add(newOption);
        pollRepository.save(poll);
    }

    @Override
    public void vote(VoteDto voteDto) {
        Optional<PollEntity> optionalPoll = pollRepository.findById(voteDto.getPollId());
        if(!optionalPoll.isPresent()) {return;}

        PollEntity poll = optionalPoll.get();

        int index=0;
        for(Long optionId: voteDto.getOptionIds()){
            Optional<OptionEntity> optionalOption = optionRepository.findById(optionId);
            if(!optionalOption.isPresent()) {return;}

            OptionEntity option = optionalOption.get();

            poll.setEntryCount(poll.getEntryCount() + voteDto.getVotePoints().get(index));
            option.setCount(option.getCount() + voteDto.getVotePoints().get(index));

            optionRepository.save(option);
            pollRepository.save(poll);

            index++;
        }
    }
}
