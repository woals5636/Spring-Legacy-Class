package org.doit.ik;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class AjaxUploadController {
	
	@GetMapping("/ajax/upload")
	public void upload() {
		log.info("AjaxUploadController upload()........");
	}
}
