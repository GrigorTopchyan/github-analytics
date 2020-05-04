package com.webbfontaine.task.githubanalytics.validator;

public class ValidationUtil {
    public static void validateGreaterThanZero(long arg, String name){
        if (arg <= 0){
            throw new IllegalArgumentException(String.format("Parameter %s must be greater than zero, actual: %d",name,arg));
        }
    }
}
