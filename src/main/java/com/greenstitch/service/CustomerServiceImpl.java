package com.greenstitch.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenstitch.exception.CustomerException;
import com.greenstitch.model.Authority;
import com.greenstitch.model.Customer;
import com.greenstitch.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * This method registers a new customer by saving the customer object into the
	 * database.
	 * 
	 * @param customer The customer object to be registered.
	 * 
	 * @return The registered customer object with updated details.
	 * 
	 * @throws CustomerException If there is an error while registering the
	 *                           customer.
	 * 
	 */
	@Override
	public Customer registerCustomer(Customer customer) throws CustomerException {
		// Set the customer for each authority associated with the customer
		List<Authority> authorities = customer.getAuthorities();

		for (Authority authority : authorities) {
			authority.setCustomer(customer);
		}

		// Save the customer object in the database and return the updated customer
		// object
		return customerRepository.save(customer);

	}

	// <--------======================================================---------> //

	/**
	 * Returns the details of the customer with the specified email.
	 * 
	 * @param email the email of the customer
	 * 
	 * @return the customer with the specified email
	 * 
	 * @throws CustomerException if a customer with the specified email is not found
	 * 
	 */
	@Override
	public Customer getCustomerDetailsByEmail(String email) throws CustomerException {

		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new CustomerException("Customer Not found with Email: " + email));
	}

	// <--------======================================================---------> //

	/**
	 * Retrieves all the customer details from the database.
	 * 
	 * @return A list of Customer objects.
	 * 
	 * @throws CustomerException If no customers are found in the database.
	 */
	@Override
	public List<Customer> getAllCustomerDetails() throws CustomerException {

		List<Customer> customers = customerRepository.findAll();

		if (customers.isEmpty())
			throw new CustomerException("No Customer find");

		return customers;

	}

}
