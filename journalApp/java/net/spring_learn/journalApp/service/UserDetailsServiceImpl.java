package net.spring_learn.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByUserName(username);
		if(user != null)
		{
			return org.springframework.security.core.userdetails.User.builder()
			.username(user.getUserName())
			.password(user.getPassword())
			.roles(user.getRoles().toArray(new String[0]))
			.build();		
			
		}
		
		throw new UsernameNotFoundException("User not found with Username" + username);
	}

}
