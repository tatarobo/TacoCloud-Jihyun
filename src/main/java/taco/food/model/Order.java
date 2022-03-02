package taco.food.model;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
	private Long id;
	private Date placedAt;

	private String deliveryName;
	private String deliveryStreet;
	private String deliveryCity;
	private String deliveryState;
	private String deliveryZip;
	private String ccNumber;
	private String ccExpiration;
	private String ccCVV;
}
