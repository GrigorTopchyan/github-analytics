package com.webbfontaine.task.githubanalytics.service.impl;

import com.webbfontaine.task.githubanalytics.exception.GithubClientException;
import com.webbfontaine.task.githubanalytics.exception.GithubServerException;
import com.webbfontaine.task.githubanalytics.model.*;
import com.webbfontaine.task.githubanalytics.service.GithubSearchService;
import com.webbfontaine.task.githubanalytics.validator.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Component
@Slf4j
public class GithubSearchServiceImpl implements GithubSearchService {
    private static final String BASE_URL = "https://api.github.com";

    private final WebClient webClient;


    public GithubSearchServiceImpl(WebClient.Builder webClientBuilder,
                                   @Value("${github.username}")
                                           String username,
                                   @Value("${github.access.token}")
                                           String accessToken) {
        this.webClient = webClientBuilder
                .baseUrl(BASE_URL).
                        filter(ExchangeFilterFunctions
                                .basicAuthentication(username, accessToken))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .filter(logRequestFilter())
                .filter(logResponseFilter())
                .build();
    }

    @Override
    public Mono<RepositoriesSearchResponse> findRepositoriesByCriteria(SearchCriteria criteria) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/repositories")
                        .queryParam("q", criteria.getQueryString())
                        .queryParam("sort", criteria.getSort())
                        .queryParam("order", criteria.getSortOrder())
                        .queryParam("page", criteria.getPage())
                        .queryParam("per_page", criteria.getPerPage())
                        .build()
                )
                .retrieve()
                .bodyToMono(RepositoriesSearchResponse.class);
    }

    @Override
    public Flux<Branch> findAllBranchesForRepository(long repositoryId) {
        ValidationUtil.validateGreaterThanZero(repositoryId, "repositoryId");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("repositories", String.valueOf(repositoryId), "branches")
                        .queryParam("per_page", 100)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(new GithubClientException("404 unsupported exception")))
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new GithubServerException("505 unsupported exception")))
                .bodyToFlux(Branch.class);
    }

    @Override
    public Flux<Commit> findAllCommitsForBranch(long repositoryId, String branchSha) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .pathSegment("repositories", String.valueOf(repositoryId), "commits")
                        .queryParam("sha", branchSha)
                        .queryParam("per_page", 1000)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(new GithubClientException("404 unsupported exception")))
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new GithubServerException("505 unsupported exception")))
                .bodyToFlux(Commit.class)
                .distinct();
    }

    @Override
    public Flux<Author> findAllCollaborators(long repositoryId) {
        return this.findAllBranchesForRepository(repositoryId)
                .map(Branch::getHeadSha)
                .flatMap(sha -> this.findAllCommitsForBranch(repositoryId, sha))
                .filter(c -> c.getAuthor() != null)
                .map(Commit::getAuthor)
                .distinct();
    }

    @Override
    public Flux<Commit> findLatestCommits(long repositoryId, int count) {
        return this.findAllBranchesForRepository(repositoryId)
                .map(Branch::getHeadSha)
                .flatMap(sha -> this.findAllCommitsForBranch(repositoryId, sha))
                .filter(c -> c.getAuthor() != null)
                .sort(Comparator.comparing(Commit::getDate))
                .distinct()
                .take(count);
    }

    @Override
    public Mono<GithubRepo> findRepositoryById(long repositoryId) {
        return webClient
                .get()
                .uri("/repositories/{id}", repositoryId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> Mono.error(new GithubClientException("404 unsupported exception")))
                .onStatus(HttpStatus::is5xxServerError,
                        clientResponse -> Mono.error(new GithubServerException("505 unsupported exception")))
                .bodyToMono(GithubRepo.class);
    }

    ExchangeFilterFunction logRequestFilter() {
        return ExchangeFilterFunction.ofRequestProcessor((clientRequest) -> {
            log.info("Sending request {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    ExchangeFilterFunction logResponseFilter() {
        return ExchangeFilterFunction.ofResponseProcessor((clientResponse) -> {
            log.info("Response status {}", clientResponse.statusCode());
            return Mono.just(clientResponse);
        });
    }
}
