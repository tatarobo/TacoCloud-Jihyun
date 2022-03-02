package taco.food.repository;

import taco.food.model.Ingredient;

import java.util.List;

public interface IngredientRepository {
	List<Ingredient> findAll();

	Ingredient findById(String id);

	Ingredient save(Ingredient ingredient);
}
