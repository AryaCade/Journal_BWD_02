package net.spring_learn.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	private UserService userService;
	
	
	@GetMapping("/health-check")
	public String healthStatus()
	{
		return "Ok";
	}
	
	
	@PostMapping("/create-user")
	public ResponseEntity<?> createUser(@RequestBody User user)
	{
		try {
			userService.saveNewEntry(user);
			return new ResponseEntity<>("User Created", HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
}
