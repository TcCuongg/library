package com.example.library.RedisObject;

import com.example.library.more.Mess;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class MessRedis {
    private String type;
    private String content;
    private String sent;
    private String email;
    public MessRedis(){}
    public MessRedis(Mess mess){
        this.type = mess.getType();
        this.content = mess.getContent();
        this.sent = mess.getSent().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.email = mess.getEmail();
    }
}
