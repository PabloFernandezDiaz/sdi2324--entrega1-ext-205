package com.uniovi.sdi2324entrega1ext205.controllers;


import com.uniovi.sdi2324entrega1ext205.entities.LoggerEntry;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.LoggerService;
import com.uniovi.sdi2324entrega1ext205.services.RolesService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import com.uniovi.sdi2324entrega1ext205.validators.EditUserFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private final UsersService usersService;
    private final RolesService rolesService;
    private final LoggerService loggerService;
    private final EditUserFormValidator editUserFormValidator;

    public AdminController(UsersService usersService, RolesService rolesService, LoggerService loggerService
            , EditUserFormValidator editUserFormValidator) {
        this.usersService = usersService;
        this.rolesService = rolesService;
        this.loggerService = loggerService;
        this.editUserFormValidator = editUserFormValidator;
    }
    @RequestMapping("/admin/user/list")
    public String getListado(Model model, Pageable pageable) {
//        Page<User> userList = usersService.getAllUsers(pageable);
//        model.addAttribute("usersList", userList.getContent());
//        model.addAttribute("page", userList);
        return "redirect:/user/list";
    }
    @RequestMapping("/admin/user/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        User user = usersService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("rolList", rolesService.getRoles());
        return "user/edit";
    }
    @RequestMapping(value = "/admin/user/edit/{id}", method = RequestMethod.POST)
    public String setEdit(Model model, @PathVariable Long id, @Validated User user, BindingResult result) {
        editUserFormValidator.validate(user,result);
        if(result.hasErrors()) {
            model.addAttribute("rolList", rolesService.getRoles());
            return "user/edit";
        }
        usersService.updateUser(user);
        return "redirect:/user/details/" + id;
    }
    @RequestMapping("/user/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("user", usersService.getUser(id));
        return "user/details";
    }

    @RequestMapping("/admin/user/delete/{id}")
    public String delete(@PathVariable Long id) {
        usersService.deleteUser(id);
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/admin/user/deleteMulti", method = RequestMethod.POST)
    public String deleteMulti(@RequestParam("usersToDelete") List<Long> userList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = usersService.getUserByEmail(auth.getName());
        userList.remove(currentUser.getId());
        usersService.deleteMultipleUsers(userList);
        return "redirect:/admin/user/list";
    }
    @RequestMapping("/admin/log")
    public String getLog(Model model,@RequestParam(defaultValue = LoggerEntry.ALL, name = "logType") String filterRole) {
        model.addAttribute("log",loggerService.getAllEntriesByType(filterRole));
        List<String> typeList = Arrays.stream(LoggerEntry.LoggerType.values())
                .map(Enum::name).collect(Collectors.toList());
        typeList.add(0,LoggerEntry.ALL);
        model.addAttribute("logTypes",typeList);
        model.addAttribute("prevRole",filterRole);
        return "log/list";
    }
    @RequestMapping("/admin/log/update")
    public String updateLog(Model model,@RequestParam(defaultValue = LoggerEntry.ALL, name = "logType") String filterRole){
        model.addAttribute("log",loggerService.getAllEntriesByType(filterRole));
        List<String> typeList = Arrays.stream(LoggerEntry.LoggerType.values())
                .map(Enum::name).collect(Collectors.toList());
        typeList.add(0,LoggerEntry.ALL);
        model.addAttribute("logTypes",typeList);
        model.addAttribute("prevRole",filterRole);
        return "log/list :: logTable";
    }
    @RequestMapping("/admin/log/delete")
    public String deleteLog(Model model,@RequestParam(name = "logType") String filterRole) {
        loggerService.deleteAllEntriesByType(filterRole);
        model.addAttribute("log",loggerService.getAllEntriesByType(filterRole));
        List<String> typeList = Arrays.stream(LoggerEntry.LoggerType.values())
                .map(Enum::name).collect(Collectors.toList());
        typeList.add(0,LoggerEntry.ALL);
        model.addAttribute("logTypes",typeList);
        model.addAttribute("prevRole",filterRole);
        return "log/list :: logTable";
    }
}
