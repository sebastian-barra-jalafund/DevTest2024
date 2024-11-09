package com.apirest.devChallenge.model;


public class Vote {
    private int pollId;
    private int optionId;
    private String voterEmail;

    public Vote(int pollId, int optionId, String voterEmail) {
        this.pollId = pollId;
        this.optionId = optionId;
        this.voterEmail = voterEmail;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

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


