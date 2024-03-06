package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class Mess {
    private String type;
    private String content;
    private Timestamp sent;
    private String email;

    public Mess(String type, String content, Timestamp sent, String email){
        this.type = type;
        this.content = content;
        this.sent = sent;
        this.email = email;
    }
}
