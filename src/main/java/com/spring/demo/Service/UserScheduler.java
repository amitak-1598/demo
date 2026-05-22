package com.spring.demo.Service;

import com.spring.demo.Constants.Sentiments;
import com.spring.demo.Repository.UserRepositoryImpl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.spring.demo.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
	private UserRepositoryImpl userRepository;
	private EmailService emailService;

	UserScheduler(UserRepositoryImpl userRepository, EmailService emailService) {
		this.userRepository = userRepository;
		this.emailService = emailService;
	}

//	@Scheduled(cron = "0 * * * * */")ss
	public void fetchSentimentsAndSendMail() {
		List<User> users = userRepository.getUsersHavingEmailAndSA();
		for (User user : users) {
			List<Sentiments> sentiments = user.getJournal_enteries().stream()
					.filter(entry -> entry.getTime().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
					.map(entry -> entry.getSentiments()).collect(Collectors.toList());
			HashMap<String, Integer> countMap = new HashMap<>();
			for (Sentiments sentiment : sentiments) {
				// get if already exist
				if (countMap.get(sentiment.name()) != null) {
					int old = countMap.get(sentiment.name());
					old += 1;

					countMap.put(sentiment.name(), old);
				} else {
					countMap.put(sentiment.name(), 1);
				}
			}
			int maxValue = 0;
			String maxKey = null;
			// traverse map
			for (String key : countMap.keySet()) {
				if (countMap.get(key) > maxValue) {
					maxValue = countMap.get(key);
					maxKey = key;
				}
			}
			// null check
			if (maxKey != null) {
				emailService.sendMail(user.getEmail(), "Most Last 7 days Sentiments",
						"The most last seven days sentiment " + maxKey);
			}
			// send mail

		}
	}

}
