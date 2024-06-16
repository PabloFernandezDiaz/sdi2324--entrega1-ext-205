package com.uniovi.sdi2324entrega1ext205.controllers;


import com.uniovi.sdi2324entrega1ext205.entities.Post;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.services.PostsService;
import com.uniovi.sdi2324entrega1ext205.services.UsersService;
import com.uniovi.sdi2324entrega1ext205.validators.AddPostFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;

@Controller
public class PostsController {
    private final PostsService postsService;
    private final UsersService usersService;
    private final HttpSession httpSession;
    private final AddPostFormValidator addPostFormValidator;

    public PostsController(PostsService postsService, HttpSession httpSession,
                           AddPostFormValidator addPostFormValidator, UsersService usersService) {
        this.postsService = postsService;
        this.httpSession = httpSession;
        this.addPostFormValidator = addPostFormValidator;
        this.usersService = usersService;
    }

    @RequestMapping(value = "/post/add", method = RequestMethod.POST)
    public String setPost(@Validated Post post, BindingResult result) {
        addPostFormValidator.validate(post, result);
        if (result.hasErrors())
            return "post/add";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User activeUser = usersService.getUserByEmail(auth.getName());
        post.setUser(activeUser);
        Date now = new Date();
        post.setDate(now);
        postsService.addPost(post);
        return "redirect:/post/list";
    }
    @RequestMapping(value = "/post/add")
    public String getPost(Model model) {
        model.addAttribute("post", new Post());
        return "post/add";
    }
    @RequestMapping("/post/list")
    public String getList(Model model, Principal principal, Pageable pageable){
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        Page<Post> posts;
        posts = postsService.getPostsForUser(user, pageable);
        model.addAttribute("postsList", posts.getContent());
        model.addAttribute("page", posts);
        return "post/list";
    }
}
