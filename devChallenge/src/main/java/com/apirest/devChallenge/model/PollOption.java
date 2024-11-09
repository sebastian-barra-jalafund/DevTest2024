package com.apirest.devChallenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PollOption {
    private int id;
    private String name;
    private int votes;
    
}
