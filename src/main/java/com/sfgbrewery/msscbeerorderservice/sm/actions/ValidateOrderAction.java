package com.sfgbrewery.msscbeerorderservice.sm.actions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.sfgbrewery.model.events.ValidateOrderRequest;
import com.sfgbrewery.msscbeerorderservice.config.JmsConfig;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrder;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.sfgbrewery.msscbeerorderservice.repository.BeerOrderRepository;
import com.sfgbrewery.msscbeerorderservice.services.BeerOrderManagerImpl;
import com.sfgbrewery.msscbeerorderservice.web.mappers.BeerOrderMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

	private final BeerOrderRepository beerOrderRepository;
	private final BeerOrderMapper beerOrderMapper;
	private final JmsTemplate jmsTemplate;
	
	
	@Override
	public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
		 String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
		 Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));
		 
		 beerOrderOptional.ifPresentOrElse(beerOrder -> {
		 jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                 .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
                 .build());
		 
		 }, () -> log.error("Order Not Found. Id: " + beerOrderId));

		 
		 log.debug("Sent Validation request to queue for order id " + beerOrderId);
	}
	
	
}
