package com.readingisgood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.readingisgood.exception.EntityNotFoundException;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		super();
		this.customerRepository = customerRepository;
	}

	public Customer findById(long id) {
		return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer", "id", id));
	}

	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email)
				.orElseThrow(() -> new EntityNotFoundException("Customer", "email", email));
	}

	public List<Customer> findByName(String name) {
		return customerRepository.findByName(name);
	}

	public List<Customer> findByAddressContaining(String address) {
		return customerRepository.findByAddressContaining(address);
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}

	public void removeById(long id) {
		customerRepository.deleteById(id);
	}

}
