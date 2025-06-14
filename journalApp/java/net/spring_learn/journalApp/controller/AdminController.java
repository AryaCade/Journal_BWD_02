package net.spring_learn.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spring_learn.journalApp.entity.JournalAppEntry;
import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers()
	{
		List<User> all = userService.getAll();
		if(all != null && !all.isEmpty())
		{
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/create-new-admin")
	public ResponseEntity<?> createAdmin(@RequestBody User user)
	{	
		try {
			userService.saveNewAdmin(user);
			return new ResponseEntity<>("Admin Created", HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
