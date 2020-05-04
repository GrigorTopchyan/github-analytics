package com.webbfontaine.task.githubanalytics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties
@Getter
@ToString
public class RepositoriesSearchResponse {
    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("items")
    private List<GithubRepo> items;
}
