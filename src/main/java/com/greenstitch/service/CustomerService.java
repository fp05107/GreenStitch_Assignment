package com.greenstitch.service;
import java.util.List;

import com.greenstitch.exception.CustomerException;
import com.greenstitch.model.Customer;

public interface CustomerService {
	
	public Customer registerCustomer(Customer customer);
	
	public Customer getCustomerDetailsByEmail(String email)throws CustomerException;
	
	public List<Customer> getAllCustomerDetails()throws CustomerException;

}
