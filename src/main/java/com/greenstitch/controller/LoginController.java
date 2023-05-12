package com.greenstitch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenstitch.model.Customer;
import com.greenstitch.repository.CustomerRepository;

@RestController
public class LoginController {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Handles GET requests to "/signIn" endpoint to return the details of the
	 * authenticated customer.
	 * 
	 * @param auth the authentication object containing the details of the
	 *             authenticated customer
	 * 
	 * @return a ResponseEntity containing the details of the authenticated customer
	 *         and a HTTP status code
	 * 
	 * @throws BadCredentialsException if the authentication fails due to invalid
	 *                                 username or password
	 */
	@GetMapping("/signIn")
	public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(Authentication auth) {

		System.out.println(auth);

		Customer customer = customerRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));

		// to get the token in body, pass HttpServletResponse inside this method
		// parameter
		// System.out.println(response.getHeaders(SecurityConstants.JWT_HEADER));

		return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);

	}

}