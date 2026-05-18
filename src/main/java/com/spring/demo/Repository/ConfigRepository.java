package com.spring.demo.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.spring.demo.entity.Config;

@Repository
public interface ConfigRepository extends MongoRepository<Config, ObjectId> {

}
