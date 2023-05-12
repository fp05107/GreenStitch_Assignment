package com.greenstitch.service;

import com.greenstitch.dto.LoginDto;
import com.greenstitch.dto.SignUpDto;
import com.greenstitch.entities.User;
import com.greenstitch.exception.UserAlreadyExistsException;
import com.greenstitch.exception.UserNotFoundException;

public interface UserService {
	
    User signUp(SignUpDto signUpDto) throws UserAlreadyExistsException;
    
    User login(LoginDto loginDto);
    
    User getUserById(Long id) throws UserNotFoundException;
    
    User getUserByUsername(String username) throws UserNotFoundException;
    
    User getUserByEmail(String email) throws UserNotFoundException;
}
