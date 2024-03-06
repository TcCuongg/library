package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
    private String email;
    private String passWord;

    public Login(String email, String passWord){
        this.email = email;
        this.passWord = passWord;
    }
}
