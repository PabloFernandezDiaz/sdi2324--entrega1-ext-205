package com.uniovi.sdi2324entrega1ext205.controllers;

import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.LoggerService;
import com.uniovi.sdi2324entrega1ext205.services.RolesService;
import com.uniovi.sdi2324entrega1ext205.services.SecurityService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import com.uniovi.sdi2324entrega1ext205.validators.SignUpFormValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final SignUpFormValidator signUpFormValidator;

    private final RolesService rolesService;
    private final UsersService usersService;

    private final SecurityService securityService;
    private final LoggerService loggerService;

    public UserController(SignUpFormValidator signUpFormValidator, RolesService rolesService, UsersService usersService
            , SecurityService securityService, LoggerService loggerService) {
        this.signUpFormValidator = signUpFormValidator;
        this.rolesService = rolesService;
        this.usersService = usersService;
        this.securityService = securityService;
        this.loggerService = loggerService;
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
        return "redirect:home";
    }
}
