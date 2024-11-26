package org.doit.ik.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@AllArgsConstructor
@RequestMapping("/common/*")
@Log4j
public class CommonController {
	
	@GetMapping("/accessError.htm")
	public String accessDenied(Model model, Authentication auth) throws Exception{ 
	      log.info("> /common/accessError.htm... GET");
	      model.addAttribute("msg", "Access Denied");
	      
	      // /WEB-INF/views/common/accessError.jsp
	      return "/common/accessError";
	   }
	
} // class






