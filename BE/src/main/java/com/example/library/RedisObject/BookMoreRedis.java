package com.example.library.RedisObject;

import com.example.library.more.BookMore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class BookMoreRedis {
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
    private String importTime;
    private String status;
    private String account;
    public BookMoreRedis(BookMore bookMore){
        this.title = bookMore.getTitle();
        this.category = bookMore.getCategory();
        this.author = bookMore.getAuthor();
        this.image = bookMore.getImage();
        this.content = bookMore.getContent();
        this.bookId = bookMore.getBookId();
        this.authorId = bookMore.getAuthorId();
        this.categoryId = bookMore.getCategoryId();
        this.cost = bookMore.getCost();
        this.follow = bookMore.getFollow();
        this.quantity = bookMore.getQuantity();
        this.sale = bookMore.getSale();
        this.id = bookMore.getId();
        this.importTime = bookMore.getImportTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.status = bookMore.getStatus();
        this.account = bookMore.getAccount();
    }
    public BookMoreRedis(){}
}
