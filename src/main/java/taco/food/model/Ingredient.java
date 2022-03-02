package taco.food.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import taco.food.code.Type;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
	private String id;
	private String name;
	private Type type;

}
