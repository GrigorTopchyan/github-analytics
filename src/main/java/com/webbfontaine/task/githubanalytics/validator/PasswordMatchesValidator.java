package com.webbfontaine.task.githubanalytics.validator;

import com.webbfontaine.task.githubanalytics.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
  implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        User user = (User) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}