package net.spring_learn.journalApp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "journal_entries")	// MongoDB instance will be like Documents(rows) 
@Data										// @Data is equivalent to @Getter, @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor
public class JournalAppEntry {

	@Id										// Making id primary key by using this annotation
	private ObjectId id;
	@NonNull
	private String title;
	
	private String content;
	
	private LocalDateTime date;
	
	
}
