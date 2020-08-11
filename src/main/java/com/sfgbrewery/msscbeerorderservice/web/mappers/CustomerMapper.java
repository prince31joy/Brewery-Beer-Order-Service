package com.sfgbrewery.msscbeerorderservice.web.mappers;

import org.mapstruct.Mapper;

import com.sfgbrewery.model.CustomerDto;
import com.sfgbrewery.msscbeerorderservice.domain.Customer;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
	
	CustomerDto customerToDto(Customer customer);

    Customer dtoToCustomer(Customer dto);

}
