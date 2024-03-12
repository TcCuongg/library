package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Send {
    private String title;
    private String content;
    public Send(String title, String content){
        this.title = title;
        this.content = content;
    }
}
