package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPayTurnover {
    private String category;
    private String timeStart;
    private String timeEnd;
    public BookPayTurnover(){}
    public BookPayTurnover(String category, String timeStart, String timeEnd){
        this.category = category;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

}
