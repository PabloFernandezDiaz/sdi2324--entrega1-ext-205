package com.uniovi.sdi2324entrega1ext205.controllers;

import com.uniovi.sdi2324entrega1ext205.entities.*;
import com.uniovi.sdi2324entrega1ext205.services.*;
import com.uniovi.sdi2324entrega1ext205.validators.SignUpFormValidator;
import com.uniovi.sdi2324entrega1ext205.validators.UserProfileAccessValidator;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final SignUpFormValidator signUpFormValidator;

    private final UserProfileAccessValidator userProfileAccessValidator;

    private final RolesService rolesService;
    private final UsersService usersService;

    private final SecurityService securityService;
    private final PostsService postsService;
    private final LoggerService loggerService;

    private final FriendshipService friendshipService;
   //private String lastST; //TODO BUSCAR FORMA DE DESACERSE DE ESTA VARIABLE

    public UserController(SignUpFormValidator signUpFormValidator, UserProfileAccessValidator userProfileAccessValidator, RolesService rolesService, UsersService usersService
            , SecurityService securityService, PostsService postsService, LoggerService loggerService, FriendshipService friendshipService) {
        this.signUpFormValidator = signUpFormValidator;
        this.userProfileAccessValidator = userProfileAccessValidator;

        this.rolesService = rolesService;
        this.usersService = usersService;
        this.securityService = securityService;
        this.postsService = postsService;
        this.loggerService = loggerService;
        this.friendshipService = friendshipService;
    }

    @RequestMapping("/user/list")
    public String getListado(Model model, Pageable pageable, @RequestParam(value="", required = false) String searchText) {
        Page<User> userList = usersService.getAllUsers(pageable);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User activeUser = usersService.getUserByEmail(auth.getName());
        String lastSearch="";
        if(searchText!= null)
            lastSearch = searchText;
        if(lastSearch != null && !lastSearch.isEmpty()){
            userList = usersService.searchUserByEmailNameAndLastname(pageable, lastSearch, activeUser.getEmail(), activeUser.getRole());
        }
        else {
            userList = usersService.getUsers(pageable, activeUser.getEmail(), activeUser.getRole());
        }

        List<UserFriendship> userDtos = usersService.getUserFriendship(activeUser,userList);
        Page<Friendship> test = friendshipService.getAllFriendshipsByUser(pageable, activeUser);
        model.addAttribute("page", userList);
        model.addAttribute("usersList",userDtos);
        model.addAttribute("searchText", lastSearch);
        return "user/list";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam(defaultValue = "false", name = "error") String hasError) {
        if(hasError.equals("true")){
            model.addAttribute("errorMessage", "error");
        }
        return "login";
    }

    @RequestMapping(value = "/login/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        model.addAttribute("logoutMessage", "logout");
        return "login";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User()); return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result, HttpServletRequest request) {
        signUpFormValidator.validate(user, result);
        if(result.hasErrors())
            return "signup";

        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
        loggerService.addEntry(request.getMethod()+request.getRequestURI(), LoggerEntry.LoggerType.ALTA);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/{id}")
    public String getUserProfile(@PathVariable Long id, Model model, Principal principal, Pageable pageable, HttpServletResponse response) throws IOException {

        User friend = usersService.getUser(id);
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);

        System.out.println(user.getId());
        if(friend == null){
            response.sendError(403);
        }
        if(user.getRole().equals(rolesService.getRoles()[0])){
            if(!friendshipService.areFriends(user,friend)){
                response.sendError(403);
                //errors.reject("403");
            }
        }
        Page<Post> posts;
        posts = postsService.getPostsForUser(friend, pageable);
        model.addAttribute("friend", friend);
        model.addAttribute("postsList", posts.getContent());
        model.addAttribute("page", posts);
        return "friend/list";
    }
}
