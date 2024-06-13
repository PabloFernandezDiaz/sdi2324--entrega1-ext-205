package com.uniovi.sdi2324entrega1ext205.validators;


import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.FriendshipService;
import com.uniovi.sdi2324entrega1ext205.services.RolesService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserProfileAccessValidator implements Validator {
    private final UsersService usersService;
    private final FriendshipService friendshipService;
    private final RolesService rolesService;
    @Value("${spring.data.web.post.description.maxlength}")
    int maxLength;

    public UserProfileAccessValidator(UsersService usersService, FriendshipService friendshipService, RolesService rolesService) {
        this.usersService = usersService;
        this.friendshipService = friendshipService;
        this.rolesService = rolesService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Long.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User activeUser = usersService.getUserByEmail(auth.getName());
        Long userId = (Long) target;
        User user = usersService.getUserById(userId);
        if(activeUser.getRole().equals(rolesService.getRoles()[0])){
            if(!friendshipService.areFriends(activeUser,user)){
                errors.reject("403");
            }
        }


    }
}
