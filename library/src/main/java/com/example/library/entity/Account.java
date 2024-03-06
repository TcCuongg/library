package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Card Number")
    private Long cardNumber;
    
    @Column(name = "Name")
    private String name;

    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "Email")
    private String email;

    @Column(name = "Phone")
    private Long phone;

    @Column(name = "Address")
    private String address;

    @Column(name = "Password")
    private String password;

    @Column(name = "Level")
    private int level;

    @Column(name = "Status")
    private String status;

    @Column(name = "Type")
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToNotification", cascade = CascadeType.ALL)
    private List<Notification> notificationsFromAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToCart", cascade = CascadeType.ALL)
    private List<Cart> cartsFromAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToBuy", cascade = CascadeType.ALL)
    private List<Buy> buysFromAccount;
}
