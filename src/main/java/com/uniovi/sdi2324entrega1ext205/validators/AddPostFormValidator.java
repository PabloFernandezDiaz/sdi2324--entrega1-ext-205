package com.uniovi.sdi2324entrega1ext205.validators;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddPostFormValidator implements Validator {
    @Value("${spring.data.web.post.description.maxlength}")
    int maxLength;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Post.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        Post post = (Post) target;
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");
//
//        if (post.getDescription().length() >= maxLength)
//            errors.rejectValue("description", "Error.addPost.description.maxlength");
//    }
}
