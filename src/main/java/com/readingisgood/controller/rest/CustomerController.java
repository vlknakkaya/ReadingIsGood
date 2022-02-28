package com.readingisgood.controller.rest;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.converter.CustomerDTOConverter;
import com.readingisgood.model.converter.OrderDTOConverter;
import com.readingisgood.model.dto.CustomerDTO;
import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.model.validator.CustomerDTOValidator;
import com.readingisgood.service.CustomerService;
import com.readingisgood.service.OrderService;

@RestController
@RequestMapping("/customer")
@Validated
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CustomerDTOConverter customerDTOConverter;
	@Autowired
	private OrderDTOConverter orderDTOConverter;
	@Autowired
	private CustomerDTOValidator customerDTOValidator;

	@InitBinder(value = "customerDTO")
	void initCustomerDTOValidator(WebDataBinder binder) {
		binder.setValidator(customerDTOValidator);
	}
	
	/**
	 * Returns all customers
	 * 
	 * @return CustomerDTO list that represents all Customers
	 */
	@GetMapping
	public List<CustomerDTO> getAllCustomers() {
		return customerDTOConverter.convertToDTOList(customerService.findAll());
	}

	/**
	 * Returns customer that has given id
	 * 
	 * @param id Customer id
	 * @return CustomerDTO that represents founded Customer
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@GetMapping("/{id}")
	public CustomerDTO getCustomerById(@PathVariable @Min(value = 0, message = "id must not be negative") long id) {
		return customerDTOConverter.convertToDTO(customerService.findById(id));
	}

	/**
	 * Updates customer that has given id with given Customer properties
	 * 
	 * @param id Customer id
	 * @param customerDTO
	 * @return CustomerDTO that represents updated Customer
	 * @throws EntityNotFoundException can be thrown if the id is not found
	 */
	@PutMapping("/{id}")
	public CustomerDTO updateCustomer(@PathVariable @Min(value = 0, message = "id must not be negative") long id, @RequestBody @Valid CustomerDTO customerDTO) {
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

		return customerDTOConverter.convertToDTO(customerService.save(entity));
	}

	/**
	 * Creates new customer by given customer properties
	 * 
	 * @param customerDTO
	 * @return CustomerDTO that represents created Customer
	 */
	@PostMapping
	public CustomerDTO createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
		Customer newEntity = customerDTOConverter.convertToEntity(customerDTO);

		return customerDTOConverter.convertToDTO(customerService.save(newEntity));
	}

	/**
	 * Removes Customer that has given id
	 * 
	 * @param id Customer id
	 */
	@DeleteMapping("/{id}")
	public void removeCustomerById(@PathVariable @Min(value = 0, message = "id must not be negative") long id) {
		customerService.removeById(id);
	}

	/**
	 * Returns all orders of the customer that has given id.
	 * Also Pagination can be used
	 * 
	 * @param customerId Customer ID
	 * @param page zero-based page index
	 * @param size the size of the page to be returned
	 * @return OrderDTO list that represents all Orders
	 */
	@GetMapping("/{customerId}/orders")
	public List<OrderDTO> getOrdersByCustomerId(@PathVariable long customerId,
									@RequestParam(name = "page", required = false) @Min(value = 0, message = "page value must not be negative") Integer page,
									@RequestParam(name = "size", required = false) @Min(value = 1, message = "size value must be greater than 0") Integer size) {
		if (page != null && size != null) {
			return orderDTOConverter.convertToDTOList(orderService.findByCustomerId(customerId, page, size));
		} else {
			return orderDTOConverter.convertToDTOList(orderService.findByCustomerId(customerId));
		}
	}

}
