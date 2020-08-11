package com.sfgbrewery.msscbeerorderservice.services.beer;

import java.util.Optional;
import java.util.UUID;

import com.sfgbrewery.model.BeerDto;

public interface BeerService {
	
	Optional<BeerDto> getBeerById(UUID uuid);

    Optional<BeerDto> getBeerByUpc(String upc);
}
