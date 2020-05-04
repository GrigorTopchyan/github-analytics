package com.webbfontaine.task.githubanalytics.api;

import com.webbfontaine.task.githubanalytics.model.GithubRepo;
import com.webbfontaine.task.githubanalytics.service.GithubRepoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/githubrepos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class GithubRepositoryApi {

    private final GithubRepoService githubRepoService;

    public GithubRepositoryApi(GithubRepoService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<GithubRepo>> getById(@PathVariable long id){
        return ResponseEntity.ok(githubRepoService.findByRepoId(id));
    }

    @PostMapping
    public ResponseEntity<Mono<GithubRepo>> addGithubRepo(@RequestBody GithubRepo githubRepo){
        return  ResponseEntity.ok(githubRepoService.save(githubRepo));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<GithubRepo>> getAll(){
        return ResponseEntity.ok(githubRepoService.findAll());
    }


    @GetMapping(value = "bookmark/ids",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<Long>> getAllRepoIds(){
        return ResponseEntity.ok(githubRepoService.getAllRepoIds());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<GithubRepo>> deleteByRepoId(@PathVariable long id){
        return ResponseEntity.ok(githubRepoService.deleteByRepoId(id));
    }
}
