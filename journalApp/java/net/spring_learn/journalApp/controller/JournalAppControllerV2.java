package net.spring_learn.journalApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.spring_learn.journalApp.entity.JournalAppEntry;
import net.spring_learn.journalApp.entity.User;
import net.spring_learn.journalApp.service.JournalEntryService;
import net.spring_learn.journalApp.service.UserService;


// Controller ---> Service ---> Repository


@RestController
@RequestMapping("/journal")
public class JournalAppControllerV2 {

	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<?> getJournalEntriesOfUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName(); 
		User user = userService.findByUserName(userName);
		
		List<JournalAppEntry> all = user.getJournalEntry();
		
		if(all != null && !all.isEmpty())
		{
			return new ResponseEntity<>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<JournalAppEntry> createEntry(@RequestBody JournalAppEntry myEntry)
	{	
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName(); 
			
			journalEntryService.saveEntries(myEntry, userName);
			return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalAppEntry> getJournalEntryById(@PathVariable ObjectId myId)
	{		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName(); 
		
		User user = userService.findByUserName(userName);
		List<JournalAppEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
		
		if(!collect.isEmpty())
		{
			Optional<JournalAppEntry> journalEntry = journalEntryService.findById(myId);
			
			if(journalEntry.isPresent())
			{
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}	
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId)    // ? this means that it is not compulsory to give entity class 
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName(); 
		
		boolean removed = journalEntryService.deleteById(myId, userName);
		
		if(removed)
		{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/id/{myId}")
	public ResponseEntity<JournalAppEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalAppEntry newEntry)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName(); 
		
		User user = userService.findByUserName(userName);
		List<JournalAppEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
			
		if(!collect.isEmpty())
		{
			Optional<JournalAppEntry> journalEntry = journalEntryService.findById(myId);
			
			if(journalEntry.isPresent())
			{
				JournalAppEntry old = journalEntry.get();
				if(old != null)
				{
					old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
					old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
					
					journalEntryService.saveEntries2(old);
					return new ResponseEntity<>(old, HttpStatus.OK);
				}
				
				return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
			}	
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
