package com.webbfontaine.task.githubanalytics.service;

import com.webbfontaine.task.githubanalytics.model.GithubRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GithubRepoService {
    Mono<GithubRepo> save(GithubRepo githubRepo);
    Flux<GithubRepo> findAll();
    Mono<GithubRepo> findById(long id);
    Mono<GithubRepo> findByRepoId(long repoId);
    Flux<Long> getAllRepoIds();

    Mono<GithubRepo> deleteByRepoId(long id);
}
