package org.doit.ik;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.doit.ik.mapper.TimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TimeMybatisController {
	
	// DI
	@Autowired
	private TimeMapper timeMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(TimeMybatisController.class);
	 
	//@RequestMapping(value = "/time", method = RequestMethod.GET)
	@GetMapping(value = "/time")
	public String time(Locale locale, Model model
			, HttpServletRequest request) {
		logger.info("> TimeMybatisController.time()... ");

		String currentTime = this.timeMapper.getTime();		
		model.addAttribute("currentTime", currentTime); 
		
		String currentNextTime =  this.timeMapper.getNextTime();
		request.setAttribute("currentNextTime", currentNextTime);
		
		return "time";
	}
	
}
