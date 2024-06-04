package com.uniovi.sdi2324entrega1ext205.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class SendFriendshipRequestValidator implements Validator {
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Friendship.class.equals(clazz);
//    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
