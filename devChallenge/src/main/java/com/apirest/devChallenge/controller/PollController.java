package com.apirest.devChallenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.devChallenge.errorHandling.ErrorResponse;
import com.apirest.devChallenge.model.Poll;
import com.apirest.devChallenge.model.Vote;
import com.apirest.devChallenge.service.PollService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    
    @GetMapping
    public ResponseEntity<List<Poll>> getPolls(){
        List<Poll> polls =  pollService.getAllPolls();
        return ResponseEntity.ok(polls);
    }

    
    @PostMapping
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        try {
            
            Poll createdPoll = pollService.createPoll(poll);
            return ResponseEntity.ok(createdPoll);  
        } catch (Exception e) {
            
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Unable to create the poll.", e.getMessage())
            );
        }
    }

    @PostMapping("/{id}/votes")
    public ResponseEntity<?> submitVote(@PathVariable int id, @RequestBody VoteRequest voteRequest) {
        try {
            Vote vote = pollService.submitVote(id, voteRequest.getOptionId(), voteRequest.getVoterEmail());
            return ResponseEntity.ok(vote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Unable to submit the vote.", e.getMessage()));
        }
    }
}

class VoteRequest {
    private int optionId;
    private String voterEmail;

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getVoterEmail() {
        return voterEmail;
    }

    public void setVoterEmail(String voterEmail) {
        this.voterEmail = voterEmail;
    }
}
