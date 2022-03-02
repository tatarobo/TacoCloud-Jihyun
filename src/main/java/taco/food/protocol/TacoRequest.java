package taco.food.protocol;

import lombok.Data;
import taco.food.model.Ingredient;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class TacoRequest {
	@NotNull
	@Size(min = 5, message = "Name must be at least 5 characters long")
	private String name;

	@Size(min = 1, message = "You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
}
