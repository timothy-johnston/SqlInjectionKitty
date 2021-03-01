package com.tj.SqlInjectionKitty.controller;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tj.SqlInjectionKitty.model.LogEntry;

@RestController
public class ApiController {

	
	@PostMapping("/api/submit-entry")
	public String handleSubmitEntry(@RequestBody LogEntry submission) {
		
		System.out.println("Here I am in the entry submission endpoint!");
		submission.getMessage();
		submission.getName();
		submission.getSubmitType();
		
		System.out.println(submission.getSubmitType());
		
		return "a json representation of the submitted entry, or just its id";
		
	}
	
	@GetMapping("/api/get-entry")
	public String handleGetEntry(@RequestParam int id) {
		
		System.out.println("Here I am in the entry submission endpoint!");
		
		return "a json representation of the requested entry";
		
	}
	
	@GetMapping("/api/get-entries")
	public String handleGetEntries() {
		
		return "json representation of all entries";
		
	}

}
