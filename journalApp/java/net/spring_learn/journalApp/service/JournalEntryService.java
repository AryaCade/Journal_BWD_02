package net.spring_learn.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import net.spring_learn.journalApp.entity.JournalAppEntry;
import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.repository.JournalEntryRepository;

//Controller ---> Service ---> Repository

@Component
public class JournalEntryService {

	@Autowired
	private JournalEntryRepository repository;
	
	@Autowired
	private UserService userService;
	
	
	@Transactional    	// If any one operation is wrong then entire method will not run
	public void saveEntries(JournalAppEntry journalEntry, String userName)
	{
		try {
			journalEntry.setDate(LocalDateTime.now());
			
			User user = userService.findByUserName(userName);
			JournalAppEntry saved = repository.save(journalEntry);
			user.getJournalEntry().add(saved);
			userService.saveUser(user);
		}
		catch(Exception e){
			
			System.out.println(e);
			throw new RuntimeException("An error occure while saving the entry", e);
		}			
	}
	
	public void saveEntries2(JournalAppEntry journalEntry) {
	
		repository.save(journalEntry);
	}
	
	
	public List<JournalAppEntry> getAll()
	{
		return repository.findAll();
	}
	
	public Optional<JournalAppEntry> findById(ObjectId myId)
	{
		return repository.findById(myId);
	}
	
	@Transactional
	public boolean deleteById(@PathVariable ObjectId myId, String userName)
	{
		boolean removed = false;
		try {
			User user = userService.findByUserName(userName);
			
			removed = user.getJournalEntry().removeIf(x -> x.getId().equals(myId));
			
			if(removed) 
			{
			repository.deleteById(myId);
			userService.saveUser(user);
			}			
		}catch(Exception ex)
		{
			System.out.println(ex);
			throw new RuntimeException("Error occured while deleting the entry");
		}

		return removed;
	}

	/*public List<JournalAppEntry> findByUserName(String userName)
	{
		
	}*/

}
