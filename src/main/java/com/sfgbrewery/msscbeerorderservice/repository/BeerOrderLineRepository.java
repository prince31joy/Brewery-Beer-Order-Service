package com.sfgbrewery.msscbeerorderservice.repository;

import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.sfgbrewery.msscbeerorderservice.domain.BeerOrderLine;

public interface BeerOrderLineRepository extends PagingAndSortingRepository<BeerOrderLine, UUID> {

}
