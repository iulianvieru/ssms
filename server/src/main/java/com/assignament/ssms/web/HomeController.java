package com.assignament.ssms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller 
public class HomeController {

	@RequestMapping(value = "/")
	public String index() {
		System.out.println("Home page requested");
		return "index";
	}

}