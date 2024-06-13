package com.uniovi.sdi2324entrega1ext205.customHandlers;

import com.uniovi.sdi2324entrega1ext205.services.FriendshipService;
import com.uniovi.sdi2324entrega1ext205.services.RolesService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class CustomUserProfileAccesVoter implements AccessDecisionVoter<FilterInvocation> {
   // private final UsersService usersService;
    private final RolesService rolesService;
    private final FriendshipService friendshipService;
//    public CustomUserProfileAccesVoter(UsersService usersService, RolesService rolesService, FriendshipService friendshipService) {
//        this.usersService = usersService;
//        //this.usersService = usersService;
//        this.rolesService = rolesService;
//        this.friendshipService = friendshipService;
//    }


    public CustomUserProfileAccesVoter(RolesService rolesService, FriendshipService friendshipService) {

        this.rolesService = rolesService;
        this.friendshipService = friendshipService;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation object, Collection<ConfigAttribute> attributes) {
       // User activeUser = usersService.getUserByEmail(authentication.getName());
        return 0;
    }
}
