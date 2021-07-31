package com.employee.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@CrossOrigin(origins = "*")
public class HomeController  {

	@RequestMapping("/")
	@ApiIgnore
	public String swaggerDocs() {
		return "redirect:swagger-ui.html";
	}

	
}
