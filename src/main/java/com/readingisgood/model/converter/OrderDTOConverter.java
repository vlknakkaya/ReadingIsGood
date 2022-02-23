package com.readingisgood.model.converter;

import org.springframework.stereotype.Component;

import com.readingisgood.model.dto.OrderDTO;
import com.readingisgood.model.entity.Order;
import com.readingisgood.util.OrderStatus;

@Component
public class OrderDTOConverter implements DTOConverter<Order, OrderDTO> {

	@Override
	public Order convertToEntity(OrderDTO dto) {
		if (dto == null) {
			return null;
		}

		Order entity = new Order();
		entity.setCustomer(null); // TODO
		entity.setCart(null); // TODO
		entity.setTotalAmount(dto.getTotalAmount());
		entity.setDate(dto.getDate());
		entity.setStatus(OrderStatus.valueOf(dto.getStatus()));

		return entity;
	}

	@Override
	public OrderDTO convertToDTO(Order entity) {
		if (entity == null) {
			return null;
		}

		OrderDTO dto = new OrderDTO();
		dto.setCustomerId(entity.getCustomer().getId());
		dto.setCart(null); // TODO
		dto.setTotalAmount(entity.getTotalAmount());
		dto.setDate(entity.getDate());
		dto.setStatus(entity.getStatus().getStatusText());

		return dto;
	}

}
