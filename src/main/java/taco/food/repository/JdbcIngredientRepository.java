package taco.food.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import taco.food.code.Type;
import taco.food.model.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {
	private final JdbcTemplate jdbc;

	@Override
	public List<Ingredient> findAll() {
		return jdbc.query("select id, name, type from ingredient", this::mapRowToIngredient);
	}

	@Override
	public Ingredient findById(String id) {
		return jdbc.queryForObject("select id, name, type from ingredient where id = ?", this::mapRowToIngredient, id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update("insert into ingredient (id, name, type) values(?, ?, ?)",
					ingredient.getId(),
					ingredient.getName(),
					ingredient.getType().name());
		return ingredient;
	}

	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id"),
							  rs.getString("name"),
							  Type.valueOf(rs.getString("type")));
	}
}
