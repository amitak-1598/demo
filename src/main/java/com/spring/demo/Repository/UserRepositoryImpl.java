package com.spring.demo.Repository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;
import com.spring.demo.entity.*;

@Component
public class UserRepositoryImpl {

	private MongoTemplate mongoTemplate;

	public UserRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<User> getUsersHavingEmailAndSA() {
		Query query = new Query();
		Criteria criteria = new Criteria();
		criteria.andOperator(Criteria.where("email").ne(null).ne("").regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),
				Criteria.where("enableSentiments").eq(true));
		query.addCriteria(criteria);
		List<User> users = mongoTemplate.find(query, User.class);
		return users;

	}

}
