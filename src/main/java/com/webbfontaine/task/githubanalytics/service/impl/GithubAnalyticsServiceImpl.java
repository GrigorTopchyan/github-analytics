package com.webbfontaine.task.githubanalytics.service.impl;

import com.webbfontaine.task.githubanalytics.model.AnalyticsReport;
import com.webbfontaine.task.githubanalytics.model.Author;
import com.webbfontaine.task.githubanalytics.model.Commit;
import com.webbfontaine.task.githubanalytics.service.GithubAnalyticsService;
import com.webbfontaine.task.githubanalytics.service.GithubSearchService;
import com.webbfontaine.task.githubanalytics.validator.ValidationUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class GithubAnalyticsServiceImpl implements GithubAnalyticsService {

    private final GithubSearchService searchService;

    public GithubAnalyticsServiceImpl(GithubSearchService searchService) {
        this.searchService = searchService;
    }

    public Flux<AnalyticsReport> calculateAnalyticsForRepository(long repositoryId, int count){
        ValidationUtil.validateGreaterThanZero(repositoryId,"repositoryId");
        ValidationUtil.validateGreaterThanZero(count,"count");
        log.info("Calculating analytics for repository {} based on latest {} commits ",repositoryId,count);
        List<Author> collaborators = searchService
                .findAllCollaborators(repositoryId)
                .toStream()
                .collect(Collectors.toList());

        List<Commit> latestCommits = searchService
                .findLatestCommits(repositoryId,count)
                .toStream().collect(Collectors.toList());

        List<AnalyticsReport> userImpactList = latestCommits.stream()
                .collect(Collectors.groupingBy(Commit::getAuthor))
                .entrySet()
                .stream()
                .map(entry -> this.calculateCommitmentPercentage(entry,latestCommits.size()))
                .sorted(Comparator.comparingDouble(AnalyticsReport::getUserImpact).reversed())
                .collect(Collectors.toList());

        return Flux.fromIterable(userImpactList);
    }

    private AnalyticsReport calculateCommitmentPercentage(Map.Entry<Author,List<Commit>> entry, int count){
        double percent = 100.0 * entry.getValue().size() / count;
        return new AnalyticsReport(entry.getKey(),percent);
    }

}
