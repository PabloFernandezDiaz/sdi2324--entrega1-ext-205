package com.uniovi.sdi2324entrega1ext205.entities;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String email;

    private String name;
    private String lastName;
    private String role;
    private String password;
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private Set<Friendship> receivedFriendships;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Friendship> sentFriendships;

    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy("date DESC")
    private Set<Post> posts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }




    public User() {
    }

    public User(Long id, String email, String name, String lastName, String role, String password, String passwordConfirm) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.fullname = name + " " + lastName;
        this.role = role;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User(String email, String name, String lastName, String role, String password, String passwordConfirm) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.fullname = name + " " + lastName;
        this.role = role;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public User(String email, String name, String lastName, String role) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
    }

    public Set<Friendship> getSentFriendships() {
        return sentFriendships;
    }

    public void setSentFriendships(Set<Friendship> sentFriendships) {
        this.sentFriendships = sentFriendships;
    }

    public Set<Friendship> getReceivedFriendships() {
        return receivedFriendships;
    }

    public void setReceivedFriendship(Set<Friendship> receivedFriendships) {
        this.receivedFriendships = receivedFriendships;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }


    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    //    public Post getLastPost(){
//        return this.posts.isEmpty() ? null : this.posts.iterator().next();
//    }
    public Post getLastPost() {
        return this.posts.isEmpty() ? null : this.posts.iterator().next();
    }
}

