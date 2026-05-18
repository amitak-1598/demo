package com.spring.demo.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.spring.demo.Constants.Sentiments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "journal_enteries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Journal {
	@Id
	private ObjectId id;
	@NonNull
	private String title;
	private String content;
	private LocalDateTime time;
	private Sentiments sentiments;

}
