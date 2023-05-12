package com.greenstitch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.greenstitch.exception.CustomerException;
import com.greenstitch.model.Customer;
import com.greenstitch.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/hello")
	public String testHandler() {
		return "Welcome to my app";
	}

	/*
	 * 
	 * { "name": "ram", "email": "ram@gmail.com", "password": "1234", "address":
	 * "delhi", "authorities":[ { "name": "ROLE_USER" }, { "name": "ROLE_ADMIN" } ]
	 * }
	 * 
	 * 
	 * 
	 */

	/**
	 * 
	 * Handles the POST request to save a new customer in the system.
	 * 
	 * @param customer the Customer object to be saved
	 * 
	 * @return ResponseEntity containing the saved Customer object and HTTP status
	 *         code 202 (ACCEPTED)
	 */
	@PostMapping("/customers")
	public ResponseEntity<Customer> saveCustomerHandler(@RequestBody Customer customer) {

		// Encode the customer password before saving it
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));

		// Call the registerCustomer method of customerService to save the customer and
		// get the saved customer object
		Customer registeredCustomer = customerService.registerCustomer(customer);

		// Return a ResponseEntity containing the saved customer object and HTTP status
		// code 202 (ACCEPTED)
		return new ResponseEntity<>(registeredCustomer, HttpStatus.ACCEPTED);

	}

//	<---------=================================================------------->

	/**
	 * 
	 * Retrieves the details of a customer with the specified email from the
	 * database.
	 * 
	 * @param email the email of the customer to retrieve
	 * @return a ResponseEntity containing the retrieved Customer and a status code
	 *         of ACCEPTED if successful
	 * @throws CustomerException if the specified email does not match any existing
	 *                           customer in the database
	 */
	@GetMapping("/customers/{email}")
	public ResponseEntity<Customer> getCustomerByEmailHandler(@PathVariable("email") String email)
			throws CustomerException {
		Customer customer = customerService.getCustomerDetailsByEmail(email);
		return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
	}

//	<---------=================================================------------->

	/**
	 * This method handles the GET request for retrieving all customer details. It
	 * calls the {@link CustomerService#getAllCustomerDetails()} method to retrieve
	 * the list of all customers from the database. If the list is empty, it throws
	 * a {@link CustomerException}. Otherwise, it returns a {@link ResponseEntity}
	 * with the list of customers and an HTTP status code of 202 (Accepted).
	 * 
	 * @return a {@link ResponseEntity} with the list of all customers and an HTTP
	 *         status code of 202 (Accepted)
	 * @throws CustomerException if no customer details are found
	 * 
	 */
	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomerHandler() {

		List<Customer> customers = customerService.getAllCustomerDetails();

		return new ResponseEntity<>(customers, HttpStatus.ACCEPTED);

	}

}
