package com.spring.demo.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spring.demo.Service.JournalService;
import com.spring.demo.entity.Journal;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

@RestController
@RequestMapping("/api/journal")
@PreAuthorize("hasRole('USER')")
public class JournalController {

	@Autowired
	private JournalService journalService;

	@PostMapping
	public ResponseEntity<Journal> saveEntry(@RequestBody Journal journal) {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Journal newEntry = this.journalService.saveEntry(username, journal);
		if (newEntry != null) {
			return new ResponseEntity<>(newEntry, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping
	public ResponseEntity<List<Journal>> getEnteriesOfUser() {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return new ResponseEntity(journalService.getAllEnteriesOfUser(username), HttpStatus.OK);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<Journal> updateEntry(@PathVariable ObjectId id, @RequestBody Journal newEntry) {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return new ResponseEntity<>(this.journalService.updateEntryByUsername(username, id, newEntry), HttpStatus.OK);

	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> deleteEntry(@PathVariable ObjectId id) {
		// get user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		if (this.journalService.deleteEntryOfUser(username, id)) {
			return new ResponseEntity<>("Entry deleted successfully.", HttpStatus.OK);
		}
		return new ResponseEntity<>("No Entry found with the given id " + id, HttpStatus.NOT_FOUND);
	}

}
