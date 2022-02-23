package com.readingisgood.model.converter;

import org.springframework.stereotype.Component;

import com.readingisgood.model.dto.CustomerDTO;
import com.readingisgood.model.entity.Customer;

@Component
public class CustomerDTOConverter implements DTOConverter<Customer, CustomerDTO> {

	@Override
	public Customer convertToEntity(CustomerDTO dto) {
		if (dto == null) {
			return null;
		}

		Customer entity = new Customer();
		entity.setEmail(dto.getEmail());
		entity.setName(dto.getName());
		entity.setAddress(dto.getAddress());

		return entity;
	}

	@Override
	public CustomerDTO convertToDTO(Customer entity) {
		if (entity == null) {
			return null;
		}

		CustomerDTO dto = new CustomerDTO();
		dto.setId(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setName(entity.getName());
		dto.setAddress(entity.getAddress());

		return dto;
	}

}
