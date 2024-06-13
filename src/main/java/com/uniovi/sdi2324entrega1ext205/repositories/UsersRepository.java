package com.uniovi.sdi2324entrega1ext205.repositories;


import com.uniovi.sdi2324entrega1ext205.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);

    User findById(String id);


//    @Query("SELECT u FROM User u WHERE u.email != ?1 AND u.role = ?2")
//    Page<User> findAllByUser(Pageable pageable, String email, String role);

    Page<User> findAllByEmailNotAndRole(Pageable pageable, String email, String role);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE (LOWER(u.email) LIKE LOWER(?1) OR LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?2)) AND (u.email != ?1 AND u.role = ?3)")
    Page<User> searchByEmailNameAndLastname(Pageable pageable, String searchText, String email, String role);

    //Page<User> findAllByEmailLikeIgnoreCase();
    Page<User> findAllByEmailContainsIgnoreCaseOrNameContainsIgnoreCaseOrLastNameContainsIgnoreCase(Pageable pageable,String email,String name,String lastname);
    Page<User> findAllByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(Pageable pageable,String email,String name,String lastname);
    Page<User> searchUsersByEmailLikeIgnoreCaseOrNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(Pageable pageable,String email,String name,String lastname);
    //    Page<User> findAllByEmailIs
    //Page<User> searchUsersByEmailAndNameAndLastNameAndRole(Pageable pageable, String searchText, String email, String role);
    //deleteAllByID se le pasa un array
}
