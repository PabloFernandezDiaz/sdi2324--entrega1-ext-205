package com.uniovi.sdi2324entrega1ext205.controllers;

import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.FriendshipService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final UsersService usersService;

    public FriendshipController(FriendshipService friendshipService, UsersService usersService) {
        this.friendshipService = friendshipService;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/friendship/send/{id}", method = RequestMethod.GET)
    public String sendRequest(Model model, @PathVariable Long id, Principal principal) {
        User user2 = usersService.getUser(id);
        User user1 = usersService.getUserByEmail(principal.getName());
        Friendship f = friendshipService.createFriendship(user1, user2);
        //error

        return "redirect:/user/list";
    }
    @RequestMapping(value = "/friendship/request/list", method = RequestMethod.GET)
    public String getList(Model model, Pageable pageable, Principal principal){
        User user =usersService.getUserByEmail(principal.getName());
        Page<Friendship> friendshipList =friendshipService.getReceivedPendingFriendshipsByUser(user,pageable);
        model.addAttribute("friendshipsRequestsList",friendshipList.getContent());
        model.addAttribute("page",friendshipList);
        return "friendship/request/list";

    }
    @RequestMapping("/friendship/request/list/update")
    public String updateList(Model model,Pageable pageable, Principal principal) {
        User user =usersService.getUserByEmail(principal.getName());
        Page<Friendship> friendshipList =friendshipService.getAllFriendshipsByUser(pageable,user);
        model.addAttribute("friendshipsRequestsList",friendshipList.getContent());
        model.addAttribute("page",friendshipList);
        return "friendship/request/list :: friendshipRequestsTable";
    }

    @RequestMapping(value = "/friendship/accept/{id}", method = RequestMethod.GET)
    public String acceptRequest(Model model, @PathVariable Long id, Principal principal){
        friendshipService.acceptFriendship(id);
        return "redirect:/friendship/request/list";
    }

    @RequestMapping(value = "/friendship/list", method = RequestMethod.GET)
    public String getFriendList(Model model, Pageable pageable, Principal principal){
        User user =usersService.getUserByEmail(principal.getName());
        Page<Friendship> friendshipList =friendshipService.getAcceptedFriendshipsByUser(user,pageable);
        model.addAttribute("currentUser",user);
        model.addAttribute("friendshipsList",friendshipList.getContent());
        model.addAttribute("page",friendshipList);
        return "friendship/list";
    }

    @RequestMapping(value = "/friendship/list/update", method = RequestMethod.GET)
    public String updateFriendList(Model model, Pageable pageable, Principal principal){
        User user =usersService.getUserByEmail(principal.getName());
        Page<Friendship> friendshipList =friendshipService.getAcceptedFriendshipsByUser(user,pageable);
        model.addAttribute("currentUser",user);
        model.addAttribute("friendshipsList",friendshipList.getContent());
        model.addAttribute("page",friendshipList);
        return "friendship/list :: friendshipsTable";

    }
}
