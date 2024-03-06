package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountSearch {
    private String email;
    private String password;
    public AccountSearch(String email, String password){
        this.email = email;
        this.password = password;
    }
}
