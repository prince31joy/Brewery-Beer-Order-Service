package com.sfgbrewery.msscbeerorderservice.sm.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.sfgbrewery.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.sfgbrewery.msscbeerorderservice.services.BeerOrderManagerImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ValidationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

	@Override
	public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
		String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
		log.error("Compensating Transaction.... Validation Failed: " + beerOrderId);
	}

}
