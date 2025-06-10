package net.spring_learn.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.repository.UserRepository;

//Controller ---> Service ---> Repository

@Component
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private static final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
	
	public void saveUser(User user)
	{	
		repository.save(user);
	}
	
	public void saveNewEntry(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		repository.save(user);
	}
	
	public void saveNewAdmin(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER", "ADMIN"));
		repository.save(user);
	}
	
	public List<User> getAll()
	{
		return repository.findAll();
	}
	
	public Optional<User> findById(ObjectId myId)
	{
		return repository.findById(myId);
	}
	
	public void deleteById(@PathVariable ObjectId myId)
	{
		repository.deleteById(myId);
		
	}
	
	public User findByUserName(String username)
	{
		return repository.findByUserName(username);
	}
	
	public User deleteByUserName(String username)
	{
		return repository.deleteByUserName(username);
	}
}
