package com.webbfontaine.task.githubanalytics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties
@Getter
@ToString
public class Commit {
    @JsonProperty("sha")
    private String sha;

    private String message;

    private LocalDateTime date;

    @JsonProperty("url")
    private String url;

    @JsonProperty("author")
    Author author;

    @SuppressWarnings("unchecked")
    @JsonProperty("commit")
    public void mapCommit(Map<String, Object> commit) {
        this.date = LocalDateTime.parse(((Map<String, Object>) commit.get("author"))
                .get("date").toString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
        this.message = commit.get("message").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commit that = (Commit) o;
        return Objects.equals(sha, that.sha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha);
    }
}
