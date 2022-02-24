package com.readingisgood.model.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.readingisgood.model.dto.CartItemDTO;
import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Book;
import com.readingisgood.model.entity.Order;
import com.readingisgood.service.BookService;
import com.readingisgood.service.CustomerService;

@Component
public class OrderDTOConverter implements DTOConverter<Order, OrderDTO> {

	@Autowired
	private BookService bookService;
	@Autowired
	private CustomerService customerService;

	@Override
	public Order convertToEntity(OrderDTO dto) {
		if (dto == null) {
			return null;
		}

		Order entity = new Order();
		entity.setCustomer(customerService.findById(dto.getCustomerId()));
		entity.setDate(dto.getDate());

		Map<Book, Integer> cart = new HashMap<>();
		dto.getCart().stream().forEach(x -> cart.put(bookService.findById(x.getBookId()), x.getCount()));
		entity.setCart(cart);
		
		return entity;
	}

	@Override
	public OrderDTO convertToDTO(Order entity) {
		if (entity == null) {
			return null;
		}

		OrderDTO dto = new OrderDTO();
		dto.setId(entity.getId());
		dto.setCustomerId(entity.getCustomer().getId());
		dto.setTotalAmount(entity.getTotalAmount());
		dto.setDate(entity.getDate());
		dto.setStatus(entity.getStatus().getStatusText());

		List<CartItemDTO> cart = new ArrayList<>();
		entity.getCart().forEach((k, v) -> cart.add(new CartItemDTO(k.getId(), v)));
		dto.setCart(cart);

		return dto;
	}

}
