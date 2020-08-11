package com.sfgbrewery.msscbeerorderservice.web.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sfgbrewery.model.BeerOrderDto;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrder;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {
	
	@Mapping(target = "customerId", source = "customer.id")
	BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);

}
