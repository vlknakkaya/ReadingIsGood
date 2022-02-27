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
import java.sql.Date;
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
import com.readingisgood.model.converter.OrderDTOConverter;
import com.readingisgood.model.dto.CartItemDTO;
import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Order;
import com.readingisgood.service.OrderService;
import com.readingisgood.util.ErrorCodes;
import com.readingisgood.util.OrderStatus;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDTOConverter orderDTOConverter;

	private final ObjectMapper mapper = new ObjectMapper();

	private static final String PATH = "/order";

	/**
	 * Tests OrderController.getAllOrders() method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetAllOrders() throws Exception {
		this.mockMvc.perform(get(PATH))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * Tests OrderController.getOrderById(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrderByValidId() throws Exception {
		List<Order> entities = orderService.findAll();
		if (!entities.isEmpty()) {
			Order entity = entities.get(0);
			
			this.mockMvc.perform(get(PATH + "/" + entity.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.id").value(entity.getId()))
					.andExpect(jsonPath("$.customerId").value(entity.getCustomer().getId()));
		}
		
		entities = null;
	}

	/**
	 * Tests OrderController.getOrderById(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrderByInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(get(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
	/**
	 * Tests OrderController.updateOrder(long, OrderDTO) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testUpdateOrder() throws Exception {
		List<Order> entities = orderService.findAll();
		if (!entities.isEmpty()) {
			Order entity = entities.get(0);
			OrderStatus status = entity.getStatus();
			
			OrderDTO dto = orderDTOConverter.convertToDTO(entity);
			dto.setStatus(OrderStatus.DELIVERED.toString());
			
			this.mockMvc.perform(put(PATH + "/" + entity.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(dto)))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.status").value(OrderStatus.DELIVERED.toString()));
			
			entity.setStatus(status);
			orderService.save(entity);
		}
		
		entities = null;
	}
	
	/**
	 * Tests OrderController.createOrder(OrderDTO) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testCreateOrder() throws Exception {
		List<Order> entities = orderService.findAll();
		if (!entities.isEmpty()) {
			Order entity = entities.get(0);
			OrderDTO dto = orderDTOConverter.convertToDTO(entity);
			dto.setDate(new Date(System.currentTimeMillis()));
			dto.setStatus(OrderStatus.SHIPPING.toString());
			
			// create temp cart
			long bookId = dto.getCart().get(0).getBookId();
			CartItemDTO cartItemDTO = new CartItemDTO(bookId, 0);
			List<CartItemDTO> cartItemDTOs = new ArrayList<>();
			cartItemDTOs.add(cartItemDTO);
			dto.setCart(cartItemDTOs);

			this.mockMvc.perform(post(PATH)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(dto)))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.date").value(dto.getDate().toString()))
					.andExpect(jsonPath("$.status").value(dto.getStatus()))
					.andReturn();
		}
		
		entities = null;
	}

	/**
	 * Tests OrderController.removeOrderById(long) method with valid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveOrderByValidId() throws Exception {
		List<Order> entities = orderService.findAll();
		if (!entities.isEmpty()) {
			Order entity = entities.get(0);

			this.mockMvc.perform(delete(PATH + "/" + entity.getId()))
					.andExpect(status().isOk());

			assertThrows(EntityNotFoundException.class, () -> orderService.findById(entity.getId()));
			
			orderService.save(entity);
		}
		
		entities = null;
	}

	/**
	 * Tests OrderController.removeOrderById(long) method with invalid id
	 * 
	 * @throws Exception
	 */
	@Test
	void testRemoveOrderByInvalidId() throws Exception {
		long id = Long.MAX_VALUE;
		
		this.mockMvc.perform(delete(PATH + "/" + id))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
	/**
	 * Tests OrderController.getOrdersByDateBetween(null, null, null) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrdersByDateBetween() throws Exception {
		this.mockMvc.perform(get(PATH + "/date"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * Tests OrderController.getOrdersByDateBetween(Date, null, null) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrdersByDateBetweenWithDay() throws Exception {
		this.mockMvc.perform(get(PATH + "/date")
				.param("day", "1994-08-03"))	
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * Tests OrderController.getOrdersByDateBetween(null, Date, Date) method
	 * 
	 * @throws Exception
	 */
	@Test
	void testGetOrdersByDateBetweenWithStartAndEnd() throws Exception {
		this.mockMvc.perform(get(PATH + "/date")
				.param("startDate", "1994-08-03")
				.param("endDate", "2020-08-03"))	
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	/**
	 * Tests OrderController.setOrderStatus(long, int) method with valid order id
	 * 
	 * @throws Exception
	 */
	@Test
	void testSetOrderStatusWithValidId() throws Exception {
		List<Order> entities = orderService.findAll();
		if (!entities.isEmpty()) {
			Order entity = entities.get(0);
			OrderStatus status = entity.getStatus();
			
			this.mockMvc.perform(put(PATH + "/" + entity.getId() + "/status/" + OrderStatus.DELIVERED.getId()))
					.andExpect(status().isOk())
					.andExpect(content().contentType(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$.status").value(OrderStatus.DELIVERED.getStatusText()));
			
			entity.setStatus(status);
			orderService.save(entity);
		}
		
		entities = null;
	}

	/**
	 * Tests OrderController.setOrderStatus(long, int) method with invalid order id
	 * 
	 * @throws Exception
	 */
	@Test
	void testSetOrderStatusWithInvalidId() throws Exception {
		long id = Long.MAX_VALUE;

		this.mockMvc.perform(put(PATH + "/" + id + "/status/" + OrderStatus.DELIVERED.getId()))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.errorCode").value(ErrorCodes.ENTITY_NOT_FOUND));
	}
	
}
