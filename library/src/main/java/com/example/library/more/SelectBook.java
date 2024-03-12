package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SelectBook {
    private String category;
    private String author;
    private String costStart;
    private String costEnd;
    private String status;

    public SelectBook(String category, String author, String costStart, String costEnd, String status){
        this.category = category;
        this.author = author;
        this.costStart = costStart;
        this.costEnd = costEnd;
        this.status = status;
    }
}
