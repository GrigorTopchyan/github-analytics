package com.webbfontaine.task.githubanalytics.api;

import com.webbfontaine.task.githubanalytics.model.AnalyticsReport;
import com.webbfontaine.task.githubanalytics.service.GithubAnalyticsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping(value = "/api/analytics", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class GithubAnalyticsApi {

    private final GithubAnalyticsService analyticsService;

    public GithubAnalyticsApi(GithubAnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/repositories/{id}")
    public ResponseEntity<Flux<AnalyticsReport>> getAnalytics(@PathVariable  long id, @RequestParam int count){
        log.info("Request analytics report for GithubRepo {} based on latest {} commits ",id,count);
        return ResponseEntity.ok(analyticsService.calculateAnalyticsForRepository(id,count));
    }
}
