package com.readingisgood.controller.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.exception.EntityNotFoundException;
import com.readingisgood.model.converter.CustomerDTOConverter;
import com.readingisgood.model.dto.CustomerDTO;
import com.readingisgood.model.entity.Customer;
import com.readingisgood.service.CustomerService;
import com.readingisgood.util.ErrorCodes;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerDTOConverter customerDTOConverter;

	private final ObjectMapper mapper = new ObjectMapper();
	
	private static final String PATH = "/customer";

	/**
	 * Tests CustomerController.getAllCustomers() method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetAllCustomers() throws Exception {
		this.mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * Tests CustomerController.getCustomerById(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetCustomerByValidId() throws Exception {
		List<Customer> entities = customerService.findAll();
		if (!entities.isEmpty()) {
			Customer entity = entities.get(0);
			
			this.mockMvc.perform(get(PATH + "/" + entity.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id").value(entity.getId()))
					.andExpect(jsonPath("$.email").value(entity.getEmail()))
					.andExpect(jsonPath("$.name").value(entity.getName()))
					.andExpect(jsonPath("$.address").value(entity.getAddress()));
		}
		
		entities = null;
	}

	/**
	 * Tests CustomerController.getCustomerById(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetCustomerByInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(get(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
	/**
	 * Tests CustomerController.updateCustomer(long, CustomerDTO) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateCustomer() throws Exception {
		CustomerDTO dto = new CustomerDTO(0, "customer@test", "test_user", "Londra", new ArrayList<>());
		Customer entity = customerService.save(customerDTOConverter.convertToEntity(dto));
		
		dto.setName("updated_test_user");
		
		this.mockMvc.perform(put(PATH + "/" + entity.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value(dto.getName()));
		
		customerService.removeById(entity.getId());
	}
	
	/**
	 * Tests CustomerController.createCustomer(CustomerDTO) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testCreateCustomer() throws Exception {
		CustomerDTO dto = new CustomerDTO(0, "customer" + Math.random() + "@test", "test_user", "Londra", new ArrayList<>());
		
		this.mockMvc.perform(post(PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.email").value(dto.getEmail()))
				.andExpect(jsonPath("$.name").value(dto.getName()))
				.andExpect(jsonPath("$.address").value(dto.getAddress()))
				.andReturn();
	}

	/**
	 * Tests CustomerController.removeCustomerById(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveCustomerByValidId() throws Exception {
		CustomerDTO dto = new CustomerDTO(0, "del_customer@test", "test_user", "Londra", new ArrayList<>());
		Customer entity = customerService.save(customerDTOConverter.convertToEntity(dto));

		this.mockMvc.perform(delete(PATH + "/" + entity.getId()))
				.andExpect(status().isOk());
		
		assertThrows(EntityNotFoundException.class, () -> customerService.findById(entity.getId()));
	}

	/**
	 * Tests CustomerController.removeCustomerById(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveCustomerByInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(delete(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
	/**
	 * Tests CustomerController.getOrdersByCustomer(long, null, null) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrdersByValidCustomer() throws Exception {
		List<Customer> entities = customerService.findAll();
		if (!entities.isEmpty()) {
			Customer entity = entities.get(0);
			
			this.mockMvc.perform(get(PATH + "/" + entity.getId() + "/orders"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		
		entities = null;
	}
	
	/**
	 * Tests CustomerController.getOrdersByCustomer(long, Integer, Integer) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrdersByValidCustomerWithPage() throws Exception {
		List<Customer> entities = customerService.findAll();
		if (!entities.isEmpty()) {
			Customer entity = entities.get(0);
			
			this.mockMvc.perform(get(PATH + "/" + entity.getId() + "/orders")
					.param("page", "1")
					.param("size", "2"))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		
		entities = null;
	}
	
	/**
	 * Tests CustomerController.getOrdersByCustomer(long, null, null) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrdersByInvalidCustomer() throws Exception {
		long id = Long.MAX_VALUE;

		this.mockMvc.perform(get(PATH + "/" + id + "/orders"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$").isEmpty());
	}

}
