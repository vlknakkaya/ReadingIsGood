package com.readingisgood.controller.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.readingisgood.model.entity.Customer;
import com.readingisgood.service.CustomerService;
import com.readingisgood.util.ErrorCodes;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class StatisticsControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CustomerService customerService;

	private static final String PATH = "/statistics";

	/**
	 * Tests StatisticsController.getAllOrderStatistics() method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetAllOrderStatistics() throws Exception {
		this.mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * Tests StatisticsController.getOrderStatisticsByCustomer(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrderStatisticsByValidCustomer() throws Exception {
		List<Customer> entities = customerService.findAll();
		if (!entities.isEmpty()) {
			Customer entity = entities.get(0);
			
			this.mockMvc.perform(get(PATH + "/" + entity.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON));
		}
		
		entities = null;
	}

	/**
	 * Tests StatisticsController.getOrderStatisticsByCustomer(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrderStatisticsByInvalidCustomer() throws Exception {
		Long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(get(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
}
