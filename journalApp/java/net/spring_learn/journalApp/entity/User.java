package net.spring_learn.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "users")	// MongoDB instance will be like Documents(rows) 
@Data							// @Data is equivalent to @Getter, @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@NoArgsConstructor
public class User {

	@Id 
	private ObjectId id;
	
	@Indexed(unique = true)
	@NonNull
	private String userName;
	
	@NonNull
	private String password;
	
	@DBRef		// It is similar to foreign key		// This means that we are creating a reference of JournalAppEntry in users collection 
	private List<JournalAppEntry> journalEntry = new ArrayList<>();

	private List<String> roles;
}
