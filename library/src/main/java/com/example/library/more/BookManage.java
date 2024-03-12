package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class BookManage {
    private Long bookId;
    private Long authorId;
    private String title;
    private Long cost;
    private String content;
    private String status;
    private int sale;
    private String category;
    private String author;

    public BookManage(){}

    public BookManage(Long bookId, Long authorId, String title, Long cost, String content, String status,
                      int sale, String category, String author){
        this.bookId = bookId;
        this.authorId = authorId;
        this.title = title;
        this.cost = cost;
        this.content = content;
        this.status = status;
        this.sale = sale;
        this.category = category;
        this.author = author;
    }
}
