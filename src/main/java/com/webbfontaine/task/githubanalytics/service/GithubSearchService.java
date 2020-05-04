package com.webbfontaine.task.githubanalytics.service;

import com.webbfontaine.task.githubanalytics.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface GithubSearchService {
    Mono<RepositoriesSearchResponse> findRepositoriesByCriteria(SearchCriteria criteria);

    Flux<Branch> findAllBranchesForRepository(long repositoryId);

    Flux<Commit> findAllCommitsForBranch(long repositoryId, String branchSha);

    Flux<Author> findAllCollaborators(long repositoryId);

    Flux<Commit> findLatestCommits(long repositoryId, int count);

    Mono<GithubRepo> findRepositoryById(long repositoryId);
}
