package com.uniovi.sdi2324entrega1ext205.validators;


import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class SendFriendshipRequestValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Long.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Long userId = (Long) target;
    }
}
