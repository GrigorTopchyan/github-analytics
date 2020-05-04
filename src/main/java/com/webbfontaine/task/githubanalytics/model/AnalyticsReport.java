package com.webbfontaine.task.githubanalytics.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class AnalyticsReport {
    private Author author;
    @JsonProperty("user_impact")
    private double userImpact;
}
