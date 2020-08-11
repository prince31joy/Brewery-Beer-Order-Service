package com.sfgbrewery.msscbeerorderservice.services;

import java.util.UUID;

import com.sfgbrewery.model.BeerOrderDto;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrder;

public interface BeerOrderManager {

	BeerOrder newBeerOrder(BeerOrder beerOrder);

	void processValidationResult(UUID beerOrderId, Boolean isValid);

	void beerOrderAllocationPassed(BeerOrderDto beerOrder);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrder);

    void beerOrderAllocationFailed(BeerOrderDto beerOrder);

	void beerOrderPickedUp(UUID id);

	void cancelOrder(UUID id);

}
