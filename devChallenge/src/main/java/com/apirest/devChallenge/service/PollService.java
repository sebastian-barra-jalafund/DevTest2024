package com.apirest.devChallenge.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.apirest.devChallenge.model.Poll;
import com.apirest.devChallenge.model.PollOption;
import com.apirest.devChallenge.model.Vote;

@Service
public class PollService {

    private final Map<Integer, Poll> polls = new HashMap<>();
    private final Set<String> submittedVotes = new HashSet<>();
    private int nextPollId = 3;
    private int nextOptionId = 1;
    
    public PollService(){
        Poll poll1 = new Poll();
        poll1.setId(1);
        poll1.setName("favorite programming language");
        poll1.setOptions(Arrays.asList(
            new PollOption(1,"C#", 150),
            new PollOption(2, "JavaScript", 200)
        ));


        Poll poll2 = new Poll();
        poll2.setId(2);
        poll2.setName("Best Framework");
        poll2.setOptions(Arrays.asList(
            new PollOption(1, "ASP.NET Core", 120),
            new PollOption(3, "Angular", 95)
        ));

        polls.put(poll1.getId(), poll1);
        polls.put(poll2.getId(), poll2);
    }
    
    public List<Poll> getAllPolls(){
        return new ArrayList<>(polls.values());
    }

    
    public Poll createPoll(Poll poll) throws Exception {
        validatePoll(poll);

        poll.setId(nextPollId++);
        
        for (PollOption option : poll.getOptions()) {
            option.setId(nextOptionId++);
            option.setVotes(0);  
        }

        
        polls.put(poll.getId(), poll);

        return poll;
    }

    public Vote submitVote(int pollId, int optionId, String voterEmail) throws Exception {
        Poll poll = polls.get(pollId);
        if (poll == null) {
            throw new Exception("Poll does not exist.");
        }

        
        PollOption option = poll.getOptions().stream()
                .filter(o -> o.getId() == optionId)
                .findFirst()
                .orElseThrow(() -> new Exception("Option does not exist."));

        
        if (!isValidEmail(voterEmail)) {
            throw new Exception("VoterEmail must be a valid email address.");
        }

        
        String voteKey = pollId + ":" + voterEmail;
        if (submittedVotes.contains(voteKey)) {
            throw new Exception("Each poll allows only one vote per email address.");
        }

        
        option.setVotes(option.getVotes() + 1);
        submittedVotes.add(voteKey);

        
        return new Vote(pollId, optionId, voterEmail);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

     
     private void validatePoll(Poll poll) throws Exception {
        if (poll.getName() == null || poll.getName().trim().isEmpty()) {
            throw new Exception("Poll name must not be empty.");
        }

        if (!poll.getName().matches("^[a-zA-Z0-9 ]+$")) {
            throw new Exception("Poll name must only contain letters, numbers, and spaces.");
        }

        if (poll.getOptions() == null || poll.getOptions().size() < 2) {
            throw new Exception("Poll must have at least two options.");
        }

        for (PollOption option : poll.getOptions()) {
            if (option.getName() == null || option.getName().trim().isEmpty()) {
                throw new Exception("Poll option names must not be empty.");
            }
        }
    }


    
}
