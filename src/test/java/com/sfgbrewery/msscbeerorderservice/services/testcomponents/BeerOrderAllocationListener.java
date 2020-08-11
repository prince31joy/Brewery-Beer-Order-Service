package com.sfgbrewery.msscbeerorderservice.services.testcomponents;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.sfgbrewery.model.events.AllocateOrderRequest;
import com.sfgbrewery.model.events.AllocateOrderResult;
import com.sfgbrewery.msscbeerorderservice.config.JmsConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationListener {
	
	private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message msg) {
    	
    	boolean pendingInventory = false;
        boolean allocationError = false;
        boolean sendResponse = true;
       
        AllocateOrderRequest request = (AllocateOrderRequest) msg.getPayload();
      
        //set allocation error
        if (request.getBeerOrderDto().getCustomerRef() != null) {
            if (request.getBeerOrderDto().getCustomerRef().equals("fail-allocation")){
                allocationError = true;
            }  else if (request.getBeerOrderDto().getCustomerRef().equals("partial-allocation")) {
                pendingInventory = true;
            } else if (request.getBeerOrderDto().getCustomerRef().equals("dont-allocate")){
                sendResponse = false;
            }
        }
        
        boolean finalPendingInventory = pendingInventory;
        
        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
        	if(finalPendingInventory) {
        		beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
        	} else {
        	beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        	}
        });     
        
        if (sendResponse) {
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder()
                        .beerOrderDto(request.getBeerOrderDto())
                        .allocationError(allocationError)
                        .pendingInventory(pendingInventory)
                        .build());
     }
    }
}