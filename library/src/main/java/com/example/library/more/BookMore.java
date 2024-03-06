package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class BookMore {
    private String title;
    private String category;
    private String author;
    private String image;
    private String content;
    private Long bookId;
    private Long authorId;
    private Long categoryId;
    private Long cost;
    private Long follow;
    private int quantity;
    private int sale;
    private Long id;
    private Timestamp importTime;
    private String status;
    public BookMore(String title, String category, String author, String image, String content, Long bookId,
                    Long authorId, Long categoryId, Long cost, Long follow, int quantity, int sale, Long id,
                    Timestamp importTime, String status){
        this.title = title;
        this.category = category;
        this.author = author;
        this.image = image;
        this.content = content;
        this.bookId = bookId;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.cost = cost;
        this.follow = follow;
        this.quantity = quantity;
        this.sale = sale;
        this.id = id;
        this.importTime = importTime;
        this.status = status;
    }
}
