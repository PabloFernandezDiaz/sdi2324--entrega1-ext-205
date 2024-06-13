package com.uniovi.sdi2324entrega1ext205.services;


import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import com.uniovi.sdi2324entrega1ext205.entities.Post;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;

@Service
public class InsertSampleDataService {

    private final UsersService usersService;
    private final RolesService rolesService;
    private final FriendshipService friendshipService;
    private final PostsService postsService;

    public InsertSampleDataService(UsersService usersService, RolesService rolesService, FriendshipService friendshipService, PostsService postsService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
        this.friendshipService = friendshipService;
        this.postsService = postsService;
    }

    @PostConstruct
    public void init() {

        User admin = new User("admin@email.com", "Admin", "User",
                rolesService.getRoles()[0], "@Dm1n1str@D0r", "@Dm1n1str@D0r");
        usersService.addUser(admin);

        for (int i = 1; i < 20; i++) {
            String userIndex = String.format("%02d", i);
            User user = new User("user" + userIndex + "@email.com", "name" + userIndex, "lastname" + userIndex,
                    rolesService.getRoles()[1], "Us3r@" + i + "-PASSW", "Us3r@" + i + "-PASSW");

            usersService.addUser(user);
            for (int j = 1; j < 20; j++) {
                Post post = new Post("Post " + j, "descripcion " + j, user);
                postsService.addPost(post);
            }
        }
        User user1 = new User("ana@gmail.com","Ana", "García",rolesService.getRoles()[0],"123456","123456");
        User user2 = new User("juan@gmail.com","Juan", "Pérez",rolesService.getRoles()[0],"123456","123456");
        User user3 = new User("jorge@gmail.com","Jorge", "Fernández",rolesService.getRoles()[0],"123456","123456");
        User user4 = new User("pepe@gmail.com","Pepe", "Ferrero",rolesService.getRoles()[0],"123456","123456");
        User user5 = new User("paca@gmail.com","Paca", "García",rolesService.getRoles()[0],"123456","123456");
        User user6 = new User("lara@gmail.com","Lara", "Griffin",rolesService.getRoles()[0],"123456","123456");
        User user7 = new User("maria@gmail.com","María", "Martínez",rolesService.getRoles()[0],"123456","123456");
        User user8 = new User("elena@gmail.com","Elena", "Díaz",rolesService.getRoles()[0],"123456","123456");
        usersService.addUser(user1);
        usersService.addUser(user2);
        usersService.addUser(user3);
        usersService.addUser(user4);
        usersService.addUser(user5);
        usersService.addUser(user6);
        usersService.addUser(user7);
        usersService.addUser(user8);
        Friendship f = new Friendship(user1, user2, friendshipService.states[0],new Date(System.currentTimeMillis()-1000));
        Friendship f2 = new Friendship(user3, user2, friendshipService.states[1]);
        Friendship f3 = new Friendship(user1, user3, friendshipService.states[1]);
        Friendship f4 = new Friendship(user4, user2, friendshipService.states[1]);
        Friendship f5 = new Friendship(user5, user2, friendshipService.states[1]);
        Friendship f6 = new Friendship(user6, user2, friendshipService.states[1]);
        Friendship f7 = new Friendship(user7, user2, friendshipService.states[1]);
        Friendship f8 = new Friendship(user8, user2, friendshipService.states[1]);
        friendshipService.addFriendship(f);
        friendshipService.addFriendship(f2);
        friendshipService.addFriendship(f3);
        friendshipService.addFriendship(f4);
        friendshipService.addFriendship(f5);
        friendshipService.addFriendship(f6);
        friendshipService.addFriendship(f7);
        friendshipService.addFriendship(f8);
        usersService.addUser(new User("admin@email.com","AdminName","AdminLastname"
                ,rolesService.getRoles()[1],"@Dm1n1str@D0r","@Dm1n1str@D0r"));
        usersService.addUser(new User("admin","Admin","Admin"
                ,rolesService.getRoles()[1],"123456","123456"));
    }
}
