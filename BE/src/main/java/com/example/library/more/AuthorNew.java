package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorNew {
    private Long id;
    private String name;
    private String phone;
    private String date;
    private String address;
    public AuthorNew(){}
    public AuthorNew(Long id, String name, String phone, String date, String address){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.address = address;
    }
}
