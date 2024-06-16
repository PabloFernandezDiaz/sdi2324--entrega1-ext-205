package com.uniovi.sdi2324entrega1ext205.services;

import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.entities.UserFriendship;
import com.uniovi.sdi2324entrega1ext205.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    
    private final RolesService rolesService;

    private final FriendshipService friendshipService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, RolesService rolesService, FriendshipService friendshipService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesService = rolesService;
        this.friendshipService = friendshipService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public List<UserFriendship> getUserFriendship(User activeUser,Page<User> userList){
        List<UserFriendship> userFriendshipList = new ArrayList<>();


        for (User user : userList.getContent()) {
            Friendship f =  friendshipService.getFriendship(activeUser, user);
            UserFriendship userDto = new UserFriendship();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            userDto.setLastName(user.getLastName());
            userDto.setHasFriendship(f!=null);
            if(f==null){
                userDto.setSameUser(user.equals(activeUser));
            }
            else{
                userDto.setAccepted(!friendshipService.isPending(f));
            }

            userFriendshipList.add(userDto);
        }
        return  userFriendshipList;
    }

    public Page<User> getUsers(Pageable pageable, String email, String role){
        if( role.equals(rolesService.getRoles()[1]))
            return getAllUsers( pageable);
        return usersRepository.findAllByEmailNotAndRole(pageable, email, role);
    }
    public Page<User> getAllUsers(Pageable pageable){
        return usersRepository.findAll(pageable);
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
    public Page<User> searchUserByEmailNameAndLastname(Pageable pageable, String searchText, String email, String role){
        searchText = "%" + searchText + "%";
        Page<User> users = usersRepository.searchByEmailNameAndLastname(pageable, searchText, email, rolesService.getRoles()[0]);
        if (  role.equals(rolesService.getRoles()[1])){
            users = usersRepository
                    .findAllByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(pageable
                            ,searchText, searchText, searchText);
        }

        return users;
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
    public User getUserById(Long id) {
        return usersRepository.findById(id).get();
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }
    public void updateUser(User user){
        User oldUser = usersRepository.findById(user.getId()).get();
        oldUser.setEmail(user.getEmail());
        oldUser.setName(user.getName());
        oldUser.setLastName(user.getLastName());
        oldUser.setRole(user.getRole());
        usersRepository.save(oldUser);

    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
    public void deleteMultipleUsers(List<Long> ids){
        usersRepository.deleteAllById(ids);
    }

}
