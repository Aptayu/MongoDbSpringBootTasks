package com.stackroute.assignment.configuration;


import com.stackroute.assignment.repository.TrackRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = TrackRepository.class)
@Configuration

public class MongoDbConfig {
}
