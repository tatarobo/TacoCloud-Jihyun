package taco.food.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import taco.food.protocol.OrderRequest;
import taco.food.repository.OrderRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes("order")
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderRepository orderRepository;

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@ModelAttribute("order") @Valid OrderRequest order,
							   Errors errors,
							   SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}

		log.info("Order Submitted: " + order);
		orderRepository.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}
