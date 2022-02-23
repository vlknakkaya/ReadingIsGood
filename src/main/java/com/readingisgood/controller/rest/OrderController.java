package com.readingisgood.controller.rest;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.readingisgood.model.converter.OrderDTOConverter;
import com.readingisgood.model.dto.CartItemDTO;
import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Order;
import com.readingisgood.service.BookService;
import com.readingisgood.service.CustomerService;
import com.readingisgood.service.OrderService;
import com.readingisgood.util.OrderStatus;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDTOConverter orderDTOConverter;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private BookService bookService;

	@GetMapping
	public List<OrderDTO> getAllOrders() {
		return orderDTOConverter.convertToDTOList(orderService.findAll());
	}

	@GetMapping("/{id}")
	public OrderDTO getOrderById(@PathVariable long id) {
		return orderDTOConverter.convertToDTO(orderService.findById(id));
	}

	@PutMapping("/{id}")
	public OrderDTO updateOrder(@PathVariable long id, @RequestBody OrderDTO orderDTO) {
		Order entity = orderService.findById(id);

		if (StringUtils.hasText(String.valueOf(orderDTO.getCustomerId()))) {
			entity.setOrderOwner(customerService.findById(orderDTO.getCustomerId()));
		}

		// TODO: cart

		if (StringUtils.hasText(String.valueOf(orderDTO.getDate()))) {
			entity.setDate(orderDTO.getDate());
		}

		if (StringUtils.hasText(orderDTO.getStatus())) {
			entity.setStatus(OrderStatus.valueOf(orderDTO.getStatus()));
		}

		return orderDTOConverter.convertToDTO(orderService.save(entity));
	}

	@PostMapping
	public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
		Order newEntity = orderDTOConverter.convertToEntity(orderDTO);

		return orderDTOConverter.convertToDTO(orderService.save(newEntity));
	}

	@DeleteMapping("/{id}")
	public void removeOrderById(@PathVariable long id) {
		orderService.removeById(id);
	}

	@GetMapping("/date")
	public List<OrderDTO> getOrdersByDateBetween(
			@RequestParam(name = "day", required = false) Date date,
			@RequestParam(name = "start", required = false, defaultValue = "1970-01-01") Date startDate,
			@RequestParam(name = "end", required = false, defaultValue = "3000-01-01") Date endDate) {
		if (date != null) {
			return orderDTOConverter.convertToDTOList(orderService.findByDate(date));
		} else {
			return orderDTOConverter.convertToDTOList(orderService.findByDateBetween(startDate, endDate));
		}
	}

	@PutMapping("/{id}/add")
	public OrderDTO addToCart(@PathVariable long id, @RequestBody List<CartItemDTO> cartItemDTOs) {
		Order order = orderService.findById(id);

		for (CartItemDTO cartItemDTO : cartItemDTOs) {
			order.addToCart(bookService.findById(cartItemDTO.getBookId()), cartItemDTO.getCount());
		}

		return orderDTOConverter.convertToDTO(orderService.save(order));
	}

}
