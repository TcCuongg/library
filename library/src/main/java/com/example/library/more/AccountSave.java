package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountSave {
    private Long cardNumber;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String level;
    private String status;
    public AccountSave(Long cardNumber, String name, String email, String phone, String address, String level, String status){
        this.cardNumber = cardNumber;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.level = level;
        this.status = status;
    }
}
