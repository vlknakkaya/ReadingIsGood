package com.readingisgood.model.converter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO Converter
 *
 * @param <E> for entity object
 * @param <D> for DTO object
 */
public interface DTOConverter<E, D> {

	E convertToEntity(D dto);

	D convertToDTO(E entity);

	default List<E> convertToEntityList(List<D> dtoList) {
		return dtoList.stream().map(this::convertToEntity).collect(Collectors.toList());
	}

	default List<D> convertToDTOList(List<E> entityList) {
		return entityList.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

}
