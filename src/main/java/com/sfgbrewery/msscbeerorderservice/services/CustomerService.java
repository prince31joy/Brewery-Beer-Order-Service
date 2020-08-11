package com.sfgbrewery.msscbeerorderservice.services;

import org.springframework.data.domain.Pageable;

import com.sfgbrewery.model.CustomerPagedList;

public interface CustomerService {

	CustomerPagedList listCustomers(Pageable pageable);
}
