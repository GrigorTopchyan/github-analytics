package com.webbfontaine.task.githubanalytics.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@JsonIgnoreProperties
@Getter
@ToString
@Document
public class GithubRepo {

    @Id
    @JsonIgnore
    String id;

    @JsonProperty("id")
    private long repoId;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("url")
    private String url;

    @JsonProperty("description")
    private String description;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("pushed_at")
    private LocalDateTime pushedAt;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("language")
    private String language;
}
