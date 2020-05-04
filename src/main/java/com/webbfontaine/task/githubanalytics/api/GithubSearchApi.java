package com.webbfontaine.task.githubanalytics.api;

import com.webbfontaine.task.githubanalytics.model.RepositoriesSearchResponse;
import com.webbfontaine.task.githubanalytics.model.SearchCriteria;
import com.webbfontaine.task.githubanalytics.service.GithubSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/search")
@Validated
@Log4j2
public class GithubSearchApi {

    private final GithubSearchService searchService;

    public GithubSearchApi(GithubSearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/repository")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<Mono<RepositoriesSearchResponse>> searchRepository(@Valid SearchCriteria criteria){
        log.info("Search for github repositories with search parameters {} ", criteria);
        return ResponseEntity.ok(searchService.findRepositoriesByCriteria(criteria));
    }
}
