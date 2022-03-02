package taco.food.repository;

import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import taco.food.model.Ingredient;
import taco.food.model.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcTacoRepository implements TacoRepository {

	private final JdbcTemplate jdbc;

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		taco.getIngredients()
			.forEach(ingredient -> saveIngredientToTaco(ingredient, tacoId));
		return taco;
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(DateTime.now().toDate());
		var pscf = new PreparedStatementCreatorFactory("insert into taco (name, createdAt) values (?, ?)", Types.VARCHAR, Types.TIMESTAMP);
		pscf.setReturnGeneratedKeys(true);
		var psc = pscf.newPreparedStatementCreator(List.of(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

		var keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		return keyHolder.getKey().longValue();
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("insert into taco_ingredients (taco, ingredient) values (?, ?)", tacoId, ingredient.getId());
	}

}
