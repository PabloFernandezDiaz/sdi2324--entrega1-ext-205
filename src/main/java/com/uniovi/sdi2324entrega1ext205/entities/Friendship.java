package com.uniovi.sdi2324entrega1ext205.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name="friendship", uniqueConstraints = {
        @UniqueConstraint(name="UniqueFriendshipPerPairOfUsers",columnNames = {"sender","receiver"})})
public class Friendship {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    private Date requestDate;

    private Date acceptanceDate;

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;
    private String state;

    public Friendship() {

    }


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }


    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Friendship(User sender, User receiver, String state) {
        this.sender = sender;
        this.receiver = receiver;
        this.state=state;
        this.requestDate = new Date(System.currentTimeMillis());
    }

    public Friendship(User sender, User receiver, String state, Date startDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.state=state;
        this.requestDate = startDate;
        this.acceptanceDate = startDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Objects.equals(sender, that.sender) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver);
    }

    public boolean isSender(User user){
        return sender.equals(user);
    }
}
