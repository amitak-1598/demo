package com.spring.demo.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.spring.demo.Constants.ExceptionType;
import com.spring.demo.Constants.MessageType;
import com.spring.demo.Exceptions.JournalEntryNotFoundException;
import com.spring.demo.Exceptions.UserNotFoundException;
import com.spring.demo.Repository.JournalRepository;
import com.spring.demo.Repository.UserRepository;
import com.spring.demo.entity.Journal;
import com.spring.demo.entity.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JournalService {

	private final Logger logger = LoggerFactory.getLogger(JournalService.class);
	@Autowired
	private JournalRepository journalRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Journal saveEntry(String username, Journal entry) {
		entry.setTime(LocalDateTime.now());
		Journal saved = journalRepository.save(entry);
		// manually assign the entry to the user
		User user = userRepository.findByUsername(username);
		user.getJournal_enteries().add(saved);
		userRepository.save(user);
		log.info(MessageType.ENTRYSAVED.getMessage());
		return saved;

	}

	public List<Journal> getAllEnteries() {
		return journalRepository.findAll();

	}

	public List<Journal> getAllEnteriesOfUser(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			log.info(MessageType.ENTRYFETCHED.getMessage());
			return user.getJournal_enteries();
		}
		log.error(ExceptionType.ENTRYNOTFOUND.getMessage());
		throw new UserNotFoundException();

	}

	public Journal getEntry(ObjectId id) {
		Optional<Journal> journal = journalRepository.findById(id);
		if (journal.isPresent()) {
			// found
			log.info(MessageType.ENTRYFETCHED.getMessage());
			return journal.get();

		}
		log.error(ExceptionType.ENTRYNOTFOUND.getMessage());
		return null;

	}

	public Journal updateEntry(ObjectId id, Journal entry) {
		Journal oldEntry = journalRepository.findById(id).orElse(null);
		if (oldEntry != null) {
			// update operation
			oldEntry.setTitle(
					entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.getTitle());
			oldEntry.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent()
					: oldEntry.getContent());
			this.journalRepository.save(oldEntry);
		}
		return oldEntry;

	}

	public Journal updateEntryByUsername(String username, ObjectId id, Journal entry) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			// find entry
			List<Journal> oldEntry = user.getJournal_enteries().stream().filter(myentry -> myentry.getId().equals(id))
					.collect(Collectors.toList());

			if (!oldEntry.isEmpty()) {
				// update operation
				oldEntry.get(0).setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle()
						: oldEntry.get(0).getTitle());
				oldEntry.get(0)
						.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent()
								: oldEntry.get(0).getContent());
				return this.journalRepository.save(oldEntry.get(0));
			} else {
				throw new JournalEntryNotFoundException();
			}
		}
		throw new UserNotFoundException();

	}

	public boolean deleteEntry(ObjectId id) {
		Journal entry = journalRepository.findById(id).orElse(null);
		if (entry != null) {
			// delete
			journalRepository.delete(entry);
			return true;
		}
		return false;

	}

	public boolean deleteEntryOfUser(String username, ObjectId id) {
		// get user
		User user = userRepository.findByUsername(username);
		if (user != null) {
			// delete entry
			Optional<Journal> journal = journalRepository.findById(id);
			if (journal.isPresent()) {
				// delete
				journalRepository.delete(journal.get());
				// remove from user
				user.getJournal_enteries().removeIf(x -> x.getId().equals(id));
				userRepository.save(user);
				return true;

			}
			throw new JournalEntryNotFoundException();

		}
		throw new UserNotFoundException();

	}

}
