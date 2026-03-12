package com.incapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.incapp.entity.my_user;
import com.incapp.service.service;

@Controller
public class myController {
	
	@Autowired 
	service serv;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/loginPage")
	public String loginPage() {
		return "login";
	}
	
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/registerUser")
	public String registerUser(@ModelAttribute my_user user,Model m) {
		
		boolean userRegistered = serv.registerUser(user);
		
		if (userRegistered) {
			System.out.println("User registered Successfully! ");
			m.addAttribute("msg","User Registered Successfully!");
		}else {
			System.out.println("User Already Exist !");
			m.addAttribute("msg","User Already exist!");
		}
		
		return "index";
	}
	
	
	@GetMapping("/adminOnly")
	public String adminOnly() {
		return "adminOnly";
	}
	
	@GetMapping("/userOnly")
	public String userOnly() {
		return "userOnly";
	}
	
	@GetMapping("/any")
	public String any() {
		return "any";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}
	
}
