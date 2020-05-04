package com.webbfontaine.task.githubanalytics.service.impl;

import com.webbfontaine.task.githubanalytics.model.GithubRepo;
import com.webbfontaine.task.githubanalytics.repository.GithubRepoRepository;
import com.webbfontaine.task.githubanalytics.service.GithubRepoService;
import com.webbfontaine.task.githubanalytics.validator.ValidationUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GithubRepoServiceImpl implements GithubRepoService {

    private final GithubRepoRepository githubRepoRepository;

    public GithubRepoServiceImpl(GithubRepoRepository githubRepoRepository) {
        this.githubRepoRepository = githubRepoRepository;
    }

    @Override
    public Mono<GithubRepo> save(GithubRepo githubRepo) {
        return githubRepoRepository.save(githubRepo);
    }

    @Override
    public Flux<GithubRepo> findAll() {
        return githubRepoRepository.findAll();
    }

    @Override
    public Mono<GithubRepo> findById(long id) {
        return githubRepoRepository.findById(id);
    }

    @Override
    public Mono<GithubRepo> findByRepoId(long repoId) {
        ValidationUtil.validateGreaterThanZero(repoId,"repositoryId");
        return githubRepoRepository.findByRepoId(repoId);
    }

    @Override
    public Flux<Long> getAllRepoIds() {
        return this.findAll().map(GithubRepo::getRepoId);
    }

    @Override
    public Mono<GithubRepo> deleteByRepoId(long id) {
        return githubRepoRepository.deleteByRepoId(id);
    }
}
