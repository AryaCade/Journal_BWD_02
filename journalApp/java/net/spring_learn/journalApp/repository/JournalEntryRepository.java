package net.spring_learn.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.spring_learn.journalApp.entity.JournalAppEntry;

public interface JournalEntryRepository extends MongoRepository<JournalAppEntry, ObjectId>{

}
