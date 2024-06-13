package com.uniovi.sdi2324entrega1ext205.services;


import com.uniovi.sdi2324entrega1ext205.entities.Friendship;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import com.uniovi.sdi2324entrega1ext205.repositories.FriendshipRepository;
import com.uniovi.sdi2324entrega1ext205.repositories.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;
    private final UsersRepository usersRepository;
    public String[] states = {"STATE_ACCEPTED", "STATE_PENDING"};
    public FriendshipService(FriendshipRepository friendshipRepository, UsersRepository usersRepository) {
        this.friendshipRepository = friendshipRepository;
        this.usersRepository = usersRepository;
    }

    public Friendship createFriendship(User user1, User user2) {

        if(getFriendship(user1, user2)==null &&
                !user1.getId().equals(user2.getId())){
            Friendship f = new Friendship(user1,user2,states[1]);
            friendshipRepository.save(f);
            return f;
        }
        return null;
    }

    public Friendship getFriendship(User user, User user2) {

        return friendshipRepository.findByUsers(user, user2);
    }

    public Friendship getFriendship(Long id) {
        return friendshipRepository.findById(id).get();
    }

    public boolean isPending(Friendship f) {
            return f.getState().equals(states[1]);
    }

    public Page<Friendship> getAllFriendshipsByUser(Pageable pageable, User user ) {
        //return friendshipRepository.findAllBySenderOrReceiverIs(pageable,user,user);
        return friendshipRepository.findByUser(pageable,user);
    }

    public void addFriendship(Friendship f) {
        friendshipRepository.save(f);
    }

    public Page<Friendship> getReceivedPendingFriendshipsByUser(User user, Pageable pageable) {
        return friendshipRepository.findAllByReceiverAndState(pageable,user,"STATE_PENDING");
    }
    public Page<Friendship> getAcceptedFriendshipsByUser(User user, Pageable pageable) {
        return friendshipRepository.findAllByUserAndState(pageable,user,"STATE_ACCEPTED");
    }

    public void acceptFriendship(Long id) {
        if(friendshipRepository.findById(id).isPresent() && isPending(friendshipRepository.findById(id).get())){
            Friendship f = friendshipRepository.findById(id).get();
            f.setState(states[0]);
            f.setAcceptanceDate(new Date(System.currentTimeMillis()));
            friendshipRepository.save(f);
        }
    }

    public boolean areFriends(User user, User friend) {
        if (getFriendship(user, friend) == null)
            return false;
        return getFriendship(user, friend).getState().equals(states[0]);
    }


}
