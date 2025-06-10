package net.spring_learn.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.repository.UserRepository;
import net.spring_learn.journalApp.service.UserService;


// Controller ---> Service ---> Repository


@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository repository;
	

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user)
	{	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName(); 
		
			User userInDB = userService.findByUserName(userName);
			
			if(userInDB != null)
			{
				userInDB.setUserName(user.getUserName());
				userInDB.setPassword(user.getPassword());
				userService.saveNewEntry(userInDB);
				
				return new ResponseEntity<>(userInDB, HttpStatus.OK);
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
	
	}
	
	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestBody User user)
	{
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		repository.deleteByUserName(authentication.getName());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);	
	}
	
	
	// This should be deleted!! [Remember]
	/*@GetMapping
	public ResponseEntity<?> getAllUser()
	{
		
		List<User> all = userService.getAll();
		
		if(all != null && !all.isEmpty())
		{
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}*/


}
