package com.sfgbrewery.msscbeerorderservice.sm.actions;

import java.util.Optional;
import java.util.UUID;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.sfgbrewery.model.events.AllocateOrderRequest;
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
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

	private final BeerOrderRepository beerOrderRepository;
	private final BeerOrderMapper beerOrderMapper;
	private final JmsTemplate jmsTemplate;

	@Override
	public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
		String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
		Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

		beerOrderOptional.ifPresentOrElse(beerOrder -> {
			jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
					AllocateOrderRequest.builder().beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder)).build());

			log.debug("Sent Allocation request to queue for order id " + beerOrderId);
		}, () -> log.error("Beer Order Not Found!"));
	}
}