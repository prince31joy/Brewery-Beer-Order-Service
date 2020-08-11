package com.sfgbrewery.msscbeerorderservice.services.listeners;

import java.util.UUID;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.sfgbrewery.model.events.AllocateOrderResult;
import com.sfgbrewery.msscbeerorderservice.config.JmsConfig;
import com.sfgbrewery.msscbeerorderservice.services.BeerOrderManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationResultListener {

	private final BeerOrderManager beerOrderManager;

	@JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
	public void listen(AllocateOrderResult result) {

		 if(!result.getAllocationError() && !result.getPendingInventory()){
	            //allocated normally
	            beerOrderManager.beerOrderAllocationPassed(result.getBeerOrderDto());
	        } else if(!result.getAllocationError() && result.getPendingInventory()) {
	            //pending inventory
	            beerOrderManager.beerOrderAllocationPendingInventory(result.getBeerOrderDto());
	        } else if(result.getAllocationError()){
	            //allocation error
	            beerOrderManager.beerOrderAllocationFailed(result.getBeerOrderDto());
	        }
	    }
}
