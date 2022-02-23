package taco.food.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import taco.food.code.Ingredient;
import taco.food.protocol.Taco;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(value = "/design", name = "")
public class DesignTacoController {

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = List.of(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
											   new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
											   new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
											   new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
											   new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
											   new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
											   new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
											   new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
											   new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
											   new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

		Arrays.stream(Ingredient.Type.values())
			  .forEach(type -> model.addAttribute(type.name().toLowerCase(), filterByType(ingredients, type)));

		model.addAttribute("taco", new Taco());
		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
		return ingredients.stream()
						  .filter(ing -> ing.getType().equals(type))
						  .collect(Collectors.toList());
	}

	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors) {
		if (errors.hasErrors()) {
			return "design";
		}

		// taco 디자인을 DB에 저장한다.

		log.info("Processing design: " + design);

		return "redirect:/orders/current";
	}
}
