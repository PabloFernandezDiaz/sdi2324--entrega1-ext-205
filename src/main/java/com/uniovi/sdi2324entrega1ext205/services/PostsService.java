package com.uniovi.sdi2324entrega1ext205.services;


import com.uniovi.sdi2324entrega1ext205.entities.Post;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.repositories.PostsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final HttpSession httpSession;
    private final UsersService usersService;

    public PostsService(PostsRepository postsRepository, HttpSession httpSession, UsersService usersService) {
        this.postsRepository = postsRepository;
        this.httpSession = httpSession;
        this.usersService = usersService;
    }
    public void addPost(Post post) {
        postsRepository.save(post);
    }
    public Page<Post> getPostsForUser(User user, Pageable pageable) {
        Page<Post> posts;
        posts = postsRepository.findAllByUser(user, pageable);
        return posts;
    }
}
