package taco.food.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import taco.common.util.ModelUtils;
import taco.food.model.Ingredient;
import taco.food.model.Taco;
import taco.food.protocol.OrderRequest;
import taco.food.protocol.TacoRequest;
import taco.food.repository.IngredientRepository;
import taco.food.repository.TacoRepository;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@SessionAttributes("order")
@RequestMapping(value = "/design", name = "")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;
	private final TacoRepository tacoRepository;

	@ModelAttribute(name = "order")
	public OrderRequest order() {
		return new OrderRequest();
	}

	@ModelAttribute(name = "taco")
	public TacoRequest taco() {
		return new TacoRequest();
	}

	@GetMapping
	public String showDesignForm(Model model) {
		ingredientRepository.findAll()
							.stream()
							.collect(Collectors.groupingBy(Ingredient::getType))
							.forEach((type, ingredients) -> model.addAttribute(type.name().toLowerCase(), ingredients));

		model.addAttribute("taco", new TacoRequest());

		return "design";
	}

	@PostMapping
	public String processDesign(@Valid TacoRequest design,
								Errors errors,
								@ModelAttribute("order") OrderRequest order) {
		if (errors.hasErrors()) {
			return "design";
		}

		// taco 디자인을 DB에 저장한다.
		var taco = ModelUtils.map(design, Taco.class);
		tacoRepository.save(taco);
		order.addDesign(taco);

		log.info("Processing design: " + design);

		return "redirect:/orders/current";
	}
}
