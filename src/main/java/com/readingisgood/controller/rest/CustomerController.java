package com.readingisgood.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.converter.CustomerDTOConverter;
import com.readingisgood.model.converter.OrderDTOConverter;
import com.readingisgood.model.dto.CustomerDTO;
import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerDTOConverter customerDTOConverter;
	@Autowired
	private OrderDTOConverter orderDTOConverter;

	@GetMapping
	public List<CustomerDTO> getAllCustomers() {
		return customerDTOConverter.convertToDTOList(customerService.findAll());
	}

	@GetMapping("/{id}")
	public CustomerDTO getCustomerById(@PathVariable long id) {
		return customerDTOConverter.convertToDTO(customerService.findById(id));
	}

	@PutMapping("/{id}")
	public CustomerDTO updateCustomer(@PathVariable long id, @RequestBody CustomerDTO customerDTO) {
		Customer entity = customerService.findById(id);

		if (StringUtils.hasText(customerDTO.getEmail())) {
			entity.setEmail(customerDTO.getEmail());
		}

		if (StringUtils.hasText(customerDTO.getName())) {
			entity.setName(customerDTO.getName());
		}

		if (StringUtils.hasText(customerDTO.getAddress())) {
			entity.setAddress(customerDTO.getAddress());
		}

		// TODO: add orders

		return customerDTOConverter.convertToDTO(customerService.save(entity));
	}

	@PostMapping
	public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
		Customer newEntity = customerDTOConverter.convertToEntity(customerDTO);

		return customerDTOConverter.convertToDTO(customerService.save(newEntity));
	}

	@DeleteMapping("/{id}")
	public void removeCustomerById(@PathVariable long id) {
		customerService.removeById(id);
	}

	@GetMapping("/{id}/orders")
	public List<OrderDTO> getOrdersByCustomer(@PathVariable long id) {
		Customer customer = customerService.findById(id);

		return orderDTOConverter.convertToDTOList(customer.getOrders());
	}

}
