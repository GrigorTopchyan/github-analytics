package com.webbfontaine.task.githubanalytics.repository;

import com.webbfontaine.task.githubanalytics.model.GithubRepo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface GithubRepoRepository extends ReactiveMongoRepository<GithubRepo,Long> {
    Mono<GithubRepo> findByRepoId(long repoId);
    Mono<GithubRepo> deleteByRepoId(long repoId);
}
