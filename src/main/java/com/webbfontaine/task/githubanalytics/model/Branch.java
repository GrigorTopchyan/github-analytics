package com.webbfontaine.task.githubanalytics.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@JsonIgnoreProperties
@Getter
@ToString
public class Branch {
    @JsonProperty("name")
    private String name;

    private String headSha;

    @JsonProperty("commit")
    public void setSha(Map<String, String> commit){
        this.headSha = commit.get("sha");
    }
}
