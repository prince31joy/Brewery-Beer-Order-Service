package com.sfgbrewery.msscbeerorderservice.domain;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer extends BaseEntity {

	@Builder
	public Customer(UUID id, Long version, Timestamp createdDate, Timestamp lastModifiedDate, String customerName,
			UUID apiKey, Set<BeerOrder> beerOrders) {
		super(id, version, createdDate, lastModifiedDate);
		this.customerName = customerName;
		this.apiKey = apiKey;
		this.beerOrders = beerOrders;
	}

	private String customerName;

	@Type(type="org.hibernate.type.UUIDCharType")
	@Column(length = 36, columnDefinition = "varchar(36)")
	private UUID apiKey;

	@OneToMany(mappedBy = "customer")
	private Set<BeerOrder> beerOrders;

}
