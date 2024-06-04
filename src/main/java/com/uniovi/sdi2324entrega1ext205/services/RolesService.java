package com.uniovi.sdi2324entrega1ext205.services;

import org.springframework.stereotype.Service;

@Service
public class RolesService {
    String[] roles = {"ROLE_STANDARD", "ROLE_ADMIN"};
    public String[] getRoles() {
        return roles;
    }
}
