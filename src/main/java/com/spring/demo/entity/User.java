package com.spring.demo.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.NonNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document(collection = "users")
public class User {

	@Id
	private ObjectId id;
	@NonNull
	@Indexed(unique = true)
	private String username;
	@NonNull
	private String password;
	@DBRef
	private List<Journal> journal_enteries = new ArrayList<Journal>();
	private List<String> roles = new ArrayList<>();
	private String email;
	private boolean enableSentiments;

}
