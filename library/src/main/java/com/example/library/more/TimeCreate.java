package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeCreate {
    private String start;
    private String end;
    private String status;
    public TimeCreate(String start, String end, String status){
        this.start = start;
        this.end = end;
        this.status = status;
    }
}
