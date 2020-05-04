package com.webbfontaine.task.githubanalytics.api;

import com.webbfontaine.task.githubanalytics.model.Author;
import com.webbfontaine.task.githubanalytics.model.Commit;
import com.webbfontaine.task.githubanalytics.model.GithubRepo;
import com.webbfontaine.task.githubanalytics.service.GithubSearchService;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class GithubApi {

    private final GithubSearchService searchService;

    public GithubApi(GithubSearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping("/repositories/{id}")
    public ResponseEntity<Mono<GithubRepo>> getRepository(@PathVariable long id){
        log.info("Request for GithubRepo with id {} ",id);
        return ResponseEntity.ok(searchService.findRepositoryById(id));
    }

    @RequestMapping("/repositories/{id}/collaborators")
    public Publisher<Author> getCollaborators(@PathVariable long id){
        log.info("Request collaborators for GithubRepo {}", id);
        return searchService.findAllCollaborators(id);
    }

    @RequestMapping("repositories/{id}/commits")
    public ResponseEntity<Flux<Commit>> getLatestCommits(@PathVariable long id, @RequestParam int count){
        log.info("Request latest {} commits for GithubRepo {}", count,id);
        return ResponseEntity.ok(searchService.findLatestCommits(id,count));
    }
}
