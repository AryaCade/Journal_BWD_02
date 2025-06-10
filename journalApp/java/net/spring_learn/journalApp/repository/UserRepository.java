package net.spring_learn.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.spring_learn.journalApp.entity.JournalAppEntry;
import net.spring_learn.journalApp.entity.User;

public interface UserRepository extends MongoRepository<User, ObjectId>{

	User findByUserName(String username);
	
	User deleteByUserName(String username);
}
