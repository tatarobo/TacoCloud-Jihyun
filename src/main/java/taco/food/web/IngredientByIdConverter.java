package taco.food.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import taco.food.model.Ingredient;
import taco.food.repository.IngredientRepository;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private final IngredientRepository ingredientRepository;

	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findById(id);
	}
}
