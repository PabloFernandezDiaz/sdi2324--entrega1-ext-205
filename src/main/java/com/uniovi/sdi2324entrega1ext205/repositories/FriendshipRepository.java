package com.uniovi.sdi2324entrega1ext205.repositories;


import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendshipRepository extends CrudRepository<Friendship, Long> {
    @Query("Select f from Friendship f where (f.sender = ?1 AND f.receiver = ?2 )OR (f.sender = ?2 AND f.receiver = ?1)")
    Friendship findByUsers(User sender, User receiver);

   // Friendship findBySenderBetween(User sender, User receiver);


    // Page<Friendship> findAllBySenderIsOrReceiverIs(Pageable pageable,User user);
    Page<Friendship> findAllBySenderOrReceiverIs(Pageable pageable,User sender, User receiver);

    List<Friendship> findAllBySenderOrReceiver(User sender, User receiver);


    @Query("SELECT f FROM  Friendship f WHERE f.sender = ?1 OR f.receiver = ?1")
    Page<Friendship> findByUser(Pageable pageable, User user);


    @Query("SELECT f FROM  Friendship f WHERE f.receiver = ?1 AND f.state=?2")
    Page<Friendship> findAllByReceiverAndState(Pageable pageable, User user, String state);

    List<Friendship> findAllByReceiverAndState(User user, String state);
    @Query("SELECT f FROM  Friendship f WHERE (f.sender = ?1 OR f.receiver = ?1) AND f.state=?2")
    Page<Friendship> findAllByUserAndState(Pageable pageable, User user, String state);
}
