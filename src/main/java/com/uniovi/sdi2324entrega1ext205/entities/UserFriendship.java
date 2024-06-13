package com.uniovi.sdi2324entrega1ext205.entities;

public class UserFriendship {
    private Long id;
    private String email;

    private String name;

    private String lastName;
    private String role;
    private boolean hasFriendship = false;
    private boolean isAccepted;
    private boolean isSameUser = false;

    public boolean isHasFriendship() {
        return hasFriendship;
    }

    public boolean isSameUser() {
        return isSameUser;
    }

    public void setSameUser(boolean sameUser) {
        isSameUser = sameUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean hasFriendship() {
        return hasFriendship;
    }

    public void setHasFriendship(boolean hasFriendship) {
        this.hasFriendship = hasFriendship;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
