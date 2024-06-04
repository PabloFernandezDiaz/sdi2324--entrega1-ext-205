package com.uniovi.sdi2324entrega1ext205.validators;


import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SignUpFormValidator implements Validator {

    private final UsersService usersService;

    public SignUpFormValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmpty(errors, "name", "Error.empty.name");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "Error.empty.lastName");
        ValidationUtils.rejectIfEmpty(errors, "email", "Error.empty.email");
        ValidationUtils.rejectIfEmpty(errors, "password", "Error.empty.password");
        ValidationUtils.rejectIfEmpty(errors, "passwordConfirm", "Error.empty.passwordConfirm");

        if(user.getEmail().trim().length() != user.getEmail().length()){
            errors.rejectValue("email", "Error.email.whitespaces");
        }
        if(user.getName().trim().length() != user.getName().length()){
            errors.rejectValue("name", "Error.name.whitespaces");
        }
        if(user.getLastName().trim().length() != user.getLastName().length()){
            errors.rejectValue("lastName", "Error.lastName.whitespaces");
        }
        if(user.getPassword().trim().length() != user.getPassword().length()){
            errors.rejectValue("password", "Error.password.whitespaces");
        }
        if(user.getPasswordConfirm().trim().length() != user.getPasswordConfirm().length()){
            errors.rejectValue("passwordConfirm", "Error.passwordConfirm.whitespaces");
        }
        Pattern email = Pattern.compile ("[a-zA-Z][@][a-zA-Z][.]");
        Pattern pattern = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
        Matcher matcher = pattern.matcher(user.getEmail());
        if(!matcher.find()){
            errors.rejectValue("email", "Error.email.format");
        }

//        String[] emailSplitted = user.getEmail().split("@");
//        if(emailSplitted.length != 2){
//            errors.rejectValue("email", "Error.email.format");
//        } else {
//            String[] secondPartEmailSplitted = emailSplitted[1].split("\\.");
//            if(secondPartEmailSplitted.length < 2){
//                errors.rejectValue("email", "Error.email.format");
//            }
//        }
        List<User> users = usersService.getUsers();
        if(users.stream().anyMatch(u-> u.getEmail().equals(user.getEmail()))){
            errors.rejectValue("email", "Error.email.already.registered");
        }


        Pattern min = Pattern.compile("[a-z]");
        Pattern mayus = Pattern.compile("[A-Z]");
        Pattern numeros = Pattern.compile("[0-9]");
        Pattern especiales = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher letraMin = min.matcher(user.getPassword());
        Matcher letraMayus = mayus.matcher(user.getPassword());
        Matcher numero = numeros.matcher(user.getPassword());
        Matcher especial = especiales.matcher(user.getPassword());
        if(!letraMin.find() || !letraMayus.find() || !numero.find() || !especial.find()) {
            errors.rejectValue("password", "Error.signup.password.robust");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Error.signup.passwordConfirm.coincidence");
        }

        if (user.getPassword().length() < 12) {
            errors.rejectValue("password", "Error.signup.password.length");}
    }
}
