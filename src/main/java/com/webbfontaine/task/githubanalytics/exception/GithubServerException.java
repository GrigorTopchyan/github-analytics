package com.webbfontaine.task.githubanalytics.exception;

public class GithubServerException extends RuntimeException{
    public GithubServerException(String message) {
        super(message);
    }

    public GithubServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
