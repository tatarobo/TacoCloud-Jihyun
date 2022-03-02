package taco.food.repository;

import taco.food.model.Order;
import taco.food.protocol.OrderRequest;

public interface OrderRepository {
	Order save(OrderRequest order);
}
