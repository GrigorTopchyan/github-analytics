package com.webbfontaine.task.githubanalytics.service;

import com.webbfontaine.task.githubanalytics.model.AnalyticsReport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GithubAnalyticsService {
    Flux<AnalyticsReport> calculateAnalyticsForRepository(long repositoryId, int count);
}
