package com.uniovi.sdi2324entrega1ext205.services;

import com.uniovi.sdi2324entrega1ext205.entities.User;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Page<User> getUsers(Pageable pageable, String email, String role){
        return usersRepository.findAllByUser(pageable, email, role);
    }
    public Page<User> getUsers(Pageable pageable){
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
        Page<User> users;
        searchText = "%" + searchText + "%";
        users = usersRepository.searchByEmailNameAndLastname(pageable, searchText, email, role);
        return users;
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
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
