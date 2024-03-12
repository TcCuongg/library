package com.example.library.RedisObject;

import com.example.library.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AccountRedis {
    private String cardNumber;
    private String name;
    private String avatar;
    private String email;
    private String phone;
    private String address;
    private String password;
    private String level;
    private String status;
    private String type;
    private String timeCreate;
    public AccountRedis(Account account){
        this.cardNumber = String.valueOf(account.getCardNumber());
        this.name = account.getName();
        this.avatar = account.getAvatar();
        this.email = account.getEmail();
        this.phone = String.valueOf(account.getPhone());
        this.address = account.getAddress();
        this.password = account.getPassword();
        this.level = String.valueOf(account.getLevel());
        this.status = account.getStatus();
        this.type = account.getType();
        this.timeCreate = account.getTimeCreate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
    public AccountRedis(){}
}
