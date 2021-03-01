package com.tj.SqlInjectionKitty.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	
	@GetMapping({"", "/", "home"})
	public String index() {
		System.out.println("test");
		return "index";
	}	

}
