package com.webbfontaine.task.githubanalytics.service.impl;

import com.webbfontaine.task.githubanalytics.model.User;
import com.webbfontaine.task.githubanalytics.repository.UserRepository;
import com.webbfontaine.task.githubanalytics.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }
}
