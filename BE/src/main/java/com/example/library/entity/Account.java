package com.example.library.entity;

import com.example.library.RedisObject.AccountRedis;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Column(name="Time Create")
    private LocalDateTime timeCreate;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToNotification", cascade = CascadeType.ALL)
    private List<Notification> notificationsFromAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToCart", cascade = CascadeType.ALL)
    private List<Cart> cartsFromAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToBuy", cascade = CascadeType.ALL)
    private List<Buy> buysFromAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToBookStorage", cascade = CascadeType.ALL)
    private List<BookStorage> bookStoragesFromAccount;

    @JsonIgnore
    @OneToMany(mappedBy = "accountToCheckPay", cascade = CascadeType.ALL)
    private List<CheckPay> checkPaysFromAccount;

    public Account(){}

    public Account(AccountRedis accountRedis){
        this.cardNumber = Long.parseLong(accountRedis.getCardNumber());
        this.name = accountRedis.getName();
        this.avatar = accountRedis.getAvatar();
        this.email = accountRedis.getEmail();
        this.phone = Long.parseLong(accountRedis.getPhone());
        this.address = accountRedis.getAddress();
        this.password = accountRedis.getPassword();
        this.level = Integer.parseInt(accountRedis.getLevel());
        this.status = accountRedis.getStatus();
        this.type = accountRedis.getType();
        this.timeCreate = LocalDateTime.parse(accountRedis.getTimeCreate(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
