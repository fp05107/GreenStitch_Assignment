package com.greenstitch.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

	private String username;
    private String email;
    private String password;
    private Set<String> roles;
	
}
