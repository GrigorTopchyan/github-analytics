package com.webbfontaine.task.githubanalytics.service;

import com.webbfontaine.task.githubanalytics.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(String username);
    Mono<User> save(User user);
    Flux<User> findAll();
    Mono<Void> deleteAll();
}
