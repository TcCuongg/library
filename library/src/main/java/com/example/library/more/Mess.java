package com.example.library.more;

import com.example.library.RedisObject.MessRedis;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class Mess {
    private String type;
    private String content;
    private LocalDateTime sent;
    private String email;

    public Mess(String type, String content, LocalDateTime sent, String email){
        this.type = type;
        this.content = content;
        this.sent = sent;
        this.email = email;
    }
    public Mess(MessRedis messRedis){
        this.type = messRedis.getType();
        this.content = messRedis.getContent();
        this.sent = LocalDateTime.parse(messRedis.getSent(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.email = messRedis.getEmail();
    }
}
