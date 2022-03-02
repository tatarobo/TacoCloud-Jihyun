package taco.food.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import taco.common.util.ModelUtils;
import taco.food.model.Order;
import taco.food.model.Taco;
import taco.food.protocol.OrderRequest;

import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {

	private SimpleJdbcInsert orderInserter;
	private SimpleJdbcInsert orderTacoInserter;
	private ObjectMapper objectMapper;

	@Autowired
	public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
		orderInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order")
														  .usingGeneratedKeyColumns("id");
		orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("taco_order_tacos");
		objectMapper = new ObjectMapper();
	}

	@Override
	public Order save(OrderRequest orderRequest) {
		Order order = ModelUtils.map(orderRequest, Order.class);
		order.setPlacedAt(DateTime.now().toDate());

		long orderId = saveOrderDetails(order);
		order.setId(orderId);
		orderRequest.getTacos()
					.forEach(taco -> saveTacoToOrder(taco, orderId));

		return order;
	}

	private long saveOrderDetails(Order order) {
		var values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());

		return orderInserter.executeAndReturnKey(values)
							.longValue();
	}

	private void saveTacoToOrder(Taco taco, long orderId) {
		orderTacoInserter.execute(Map.of("tacoOrder", orderId,
										 "taco", taco.getId()));
	}
}
