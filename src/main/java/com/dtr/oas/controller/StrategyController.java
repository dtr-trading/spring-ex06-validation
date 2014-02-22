package com.dtr.oas.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dtr.oas.dto.StrategyDTO;
import com.dtr.oas.dto.StrategyMapper;
import com.dtr.oas.model.Strategy;
import com.dtr.oas.service.StrategyService;

@Controller
@RequestMapping(value="/strategy")
public class StrategyController {
	static Logger logger = LoggerFactory.getLogger(StrategyController.class);

	@Autowired
	private StrategyService strategyService;

	@RequestMapping(value="/list",  method=RequestMethod.GET)
	public String listOfStrategies(Model model) {
		List<Strategy> strategies = strategyService.getStrategies();
		model.addAttribute("strategies", strategies);

		if(! model.containsAttribute("strategyDTO")) {
			logger.info("Adding StrategyDTO object to model");
			StrategyDTO strategyDTO = new StrategyDTO();
			model.addAttribute("strategyDTO", strategyDTO);
		}
		return "strategy-list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addingStrategy(@Valid @ModelAttribute StrategyDTO strategyDTO, 
								 BindingResult result,  
								 Model model, 
								 RedirectAttributes redirectAttrs) {
		
		if (result.hasErrors()) {
			logger.info("Strategy-add error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.strategyDTO", result);
			redirectAttrs.addFlashAttribute("strategyDTO", strategyDTO);
			return "redirect:/strategy/list";
		} else {
			Strategy strategy = StrategyMapper.getStrategy(strategyDTO);
			strategyService.addStrategy(strategy);
			String message = "Strategy " + strategy.getId() + " was successfully added";
			model.addAttribute("message", message);
			return "redirect:/strategy/list";
		}
	}
	
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String editStrategyPage(@RequestParam(value="id", required=true) Integer id, Model model) {
		
		logger.info("Strategy/edit-GET:  ID to query = " + id);

		if(! model.containsAttribute("strategyDTO")) {
			logger.info("Adding StrategyDTO object to model");
			Strategy strategy = strategyService.getStrategy(id);
			logger.info("Strategy/edit-GET:  " + strategy.getString());
			StrategyDTO strategyDTO = StrategyMapper.getDTO(strategy);
			model.addAttribute("strategyDTO", strategyDTO);
		}
		
		return "strategy-edit";
	}
		
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editingStrategy(@Valid @ModelAttribute StrategyDTO strategyDTO, 
			 						    BindingResult result, 
			                            Model model, 
			                            RedirectAttributes redirectAttrs,
			                            @RequestParam(value="action", required=true) String action) {

		if (result.hasErrors()) {
			logger.info("Strategy-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.strategyDTO", result);
			redirectAttrs.addFlashAttribute("strategyDTO", strategyDTO);
			return "redirect:/strategy/edit?id=" + strategyDTO.getId();
		} else if (action.equals("save")) {
			Strategy strategy = StrategyMapper.getStrategy(strategyDTO);
			logger.info("Strategy/edit-POST:  " + strategyDTO.getString());
			strategyService.updateStrategy(strategy);
			String message = "Strategy " + strategy.getId() + " was successfully edited";
			model.addAttribute("message", message);
		} else if (action.equals("cancel")) {
			String message = "Strategy " + strategyDTO.getId() + " edit cancelled";
			model.addAttribute("message", message);
		}
		
		return "redirect:/strategy/list";
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String deleteStrategyPage(@RequestParam(value="id", required=true) Integer id, 
			@RequestParam(value="phase", required=true) String phase, Model model) {
		
		Strategy strategy = strategyService.getStrategy(id);
		logger.info("Strategy/delete-GET | id = " + id + " | phase = " + phase + " | " + strategy.getString());

		if (phase.equals("stage")) {
			StrategyDTO strategyDTO = StrategyMapper.getDTO(strategy);
			String message = "Strategy " + strategy.getId() + " queued for display.";
			model.addAttribute("strategyDTO",strategyDTO);
			model.addAttribute("message", message);
			return "strategy-delete";
		} else if (phase.equals("confirm")) {
			strategyService.deleteStrategy(id);
			String message = "Strategy " + strategy.getId() + " was successfully deleted";
			model.addAttribute("message", message);
			return "redirect:/strategy/list";
		} else if (phase.equals("cancel")) {
			String message = "Strategy delete was cancelled.";
			model.addAttribute("message", message);
			return "redirect:/strategy/list";
		}
		
		return "redirect:/strategy/list";
	}
}
