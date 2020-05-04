package com.webbfontaine.task.githubanalytics.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("jwt_token")
    private String accessToken;
}
