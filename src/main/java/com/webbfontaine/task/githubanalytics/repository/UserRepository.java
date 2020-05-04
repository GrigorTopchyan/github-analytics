package com.webbfontaine.task.githubanalytics.repository;

import com.webbfontaine.task.githubanalytics.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User,Long> {
    Mono<User> findByUsername(String username);
}
