package com.spring.demo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.demo.entity.*;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
	public User findByUsername(String username);

	public boolean deleteByUsername(String username);
}
