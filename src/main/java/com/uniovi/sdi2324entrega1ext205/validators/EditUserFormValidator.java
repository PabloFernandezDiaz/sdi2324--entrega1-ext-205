package com.uniovi.sdi2324entrega1ext205.validators;

import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.RolesService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

@Component
public class EditUserFormValidator implements Validator {

    private final UsersService usersService;

    private final RolesService rolesService;

    public EditUserFormValidator(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
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
        ValidationUtils.rejectIfEmpty(errors, "role", "Error.empty.role");

        if(user.getEmail().trim().length() != user.getEmail().length()){
            errors.rejectValue("email", "Error.email.whitespaces");
        }
        if(user.getName().trim().length() != user.getName().length()){
            errors.rejectValue("name", "Error.name.whitespaces");
        }
        if(user.getLastName().trim().length() != user.getLastName().length()){
            errors.rejectValue("lastName", "Error.lastName.whitespaces");
        }

        String[] emailSplitted = user.getEmail().split("@");
        if(emailSplitted.length != 2){
            errors.rejectValue("email", "Error.email.format");
        } else {
            String[] secondPartEmailSplitted = emailSplitted[1].split("\\.");
            if(secondPartEmailSplitted.length < 2){
                errors.rejectValue("email", "Error.email.format");
            }
        }
        List<User> users = usersService.getUsers();
        if(users.stream().anyMatch(u-> u.getEmail().equals(user.getEmail()))){
            errors.rejectValue("email", "Error.email.already.registered");
        }

        if(Arrays.stream(rolesService.getRoles()).noneMatch(r -> r.equals(user.getRole()))){
            errors.rejectValue("role", "Error.role.invalid");
        }

    }
}
