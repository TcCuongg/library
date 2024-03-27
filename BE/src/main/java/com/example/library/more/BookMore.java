package com.example.library.more;

import com.example.library.RedisObject.BookMoreRedis;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private LocalDateTime importTime;
    private String status;
    private String account;
    public BookMore(String title, String category, String author, String image, String content, Long bookId,
                     Long authorId, Long categoryId, Long cost, Long follow, int quantity, int sale, Long id,
                     LocalDateTime importTime, String status, String account){
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
        this.account = account;
    }
    public BookMore(String title, String category, String author, String image, String content, Long bookId,
                    Long authorId, Long categoryId, Long cost, Long follow, int quantity, int sale, Long id,
                    LocalDateTime importTime, String status){
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
    public BookMore(BookMoreRedis bookMoreRedis){
        this.title = bookMoreRedis.getTitle();
        this.category = bookMoreRedis.getCategory();
        this.author = bookMoreRedis.getAuthor();
        this.image = bookMoreRedis.getImage();
        this.content = bookMoreRedis.getContent();
        this.bookId = bookMoreRedis.getBookId();
        this.authorId = bookMoreRedis.getAuthorId();
        this.categoryId = bookMoreRedis.getCategoryId();
        this.cost = bookMoreRedis.getCost();
        this.follow = bookMoreRedis.getFollow();
        this.quantity = bookMoreRedis.getQuantity();
        this.sale = bookMoreRedis.getSale();
        this.id = bookMoreRedis.getId();
        this.importTime = LocalDateTime.parse(bookMoreRedis.getImportTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.status = bookMoreRedis.getStatus();
        this.account = bookMoreRedis.getAccount();
    }
}
