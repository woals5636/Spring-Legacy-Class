package org.doit.ik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/city/*")
public class CityController {

	@GetMapping("/london")
	public String london() {
		log.info("> CityController.london() GET...");
		
		return "city/london.tiles";	// "tilesViewResolver"     리턴값 마음대로줘도됨
	}
	
	@GetMapping("/paris")
	public String paris() {
		log.info("> CityController.paris() GET...");
		
		return "city/paris.tiles";	// "tilesViewResolver"     리턴값 마음대로줘도됨
	}
	
	@GetMapping("/seoul")
	public String seoul() {
		log.info("> CityController.seoul() GET...");
		
		return "city/seoul.tiles";	// "tilesViewResolver"     리턴값 마음대로줘도됨
	}
}
