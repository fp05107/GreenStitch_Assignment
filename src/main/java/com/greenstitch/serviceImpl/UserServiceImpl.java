package com.greenstitch.serviceImpl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenstitch.dto.LoginDto;
import com.greenstitch.dto.SignUpDto;
import com.greenstitch.entities.Role;
import com.greenstitch.entities.User;
import com.greenstitch.exception.UserAlreadyExistsException;
import com.greenstitch.exception.UserNotFoundException;
import com.greenstitch.repository.UserRepository;
import com.greenstitch.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Registers a new user account based on the information provided in the given
	 * SignUpDto.
	 *
	 * @param signUpDto a DTO containing the user's username, email, password, and
	 *                  desired roles.
	 * @return the newly created User entity.
	 * @throws UserAlreadyExistsException if a user with the same username or email
	 *                                    already exists in the system.
	 */
	@Override
	public User signUp(SignUpDto signUpDto) throws UserAlreadyExistsException {
		// Check if a user with the same username or email already exists
		Optional<User> nameOpt = userRepository.findByUsername(signUpDto.getUsername());
		if (nameOpt.isPresent()) {
			throw new UserAlreadyExistsException("Username already exists");
		}
		Optional<User> emailOpt = userRepository.findByEmail(signUpDto.getEmail());
		if (emailOpt.isPresent()) {
			throw new UserAlreadyExistsException("Email already exists");
		}

		// Create a new user entity based on the information provided in the DTO
		User user = new User();
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		user.setRoles(Collections.singleton(Role.USER));

		// Save the user entity in the database and return it
		return userRepository.save(user);
	}

//	<-----------==========================================================---------->  //
	/**
	 * 
	 */
	@Override
	public User login(LoginDto loginDto) {

		Optional<User> opt = userRepository.findByUsername(loginDto.getUsername());

		if (opt.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password");
		} else {
			User user = opt.get();

			if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
				throw new UsernameNotFoundException("Invalid username or password");
			}

//			String token = jwtUtils.generateToken(user);

//			user.setToken(token);

			return user;
		}

	}

//	<-----------==========================================================---------->  //

	/**
	 * Retrieves the User entity with the specified ID from the database.
	 *
	 * @param id the ID of the user to retrieve.
	 * @return the User entity with the specified ID.
	 * @throws UserNotFoundException if no user with the specified ID can be found
	 *                               in the database.
	 */
	@Override
	public User getUserById(Long id) throws UserNotFoundException {
		// Attempt to retrieve the user entity from the database by its ID
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Does not exist with the given id : " + id));
	}

//	<-----------==========================================================---------->  //

	/**
	 * Retrieves the User entity with the specified username from the database.
	 *
	 * @param username the username of the user to retrieve.
	 * @return the User entity with the specified username.
	 * @throws UserNotFoundException if no user with the specified username can be
	 *                               found in the database.
	 */
	@Override
	public User getUserByUsername(String username) throws UserNotFoundException {
		// Attempt to retrieve the user entity from the database by its username
		return userRepository.findByUsername(username).orElseThrow(
				() -> new UserNotFoundException("User does not exist with the given username : " + username));
	}

//	<-----------==========================================================---------->  //

	/**
	 * Retrieves the User entity with the specified email from the database.
	 *
	 * @param email the email of the user to retrieve.
	 * @return the User entity with the specified email.
	 * @throws UserNotFoundException if no user with the specified email can be
	 *                               found in the database.
	 */
	@Override
	public User getUserByEmail(String email) throws UserNotFoundException {
		// Attempt to retrieve the user entity from the database by its email
		Optional<User> opt = userRepository.findByEmail(email);
		if (opt.isEmpty()) {
			// Throw an exception if no user with the specified email can be found in the
			// database
			throw new UserNotFoundException("User not found with the email: " + email);
		}
		// Return the user entity if it was found in the database
		return opt.get();
	}

}
