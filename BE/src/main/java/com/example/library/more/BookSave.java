package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookSave {
    private Long bookId;
    private Long authorId;
    private Long categoryId;
    private String title;
    private String category;
    private String author;
    private String image;
    private String content;
    public BookSave(Long bookId, Long authorId, Long categoryId, String title, String category, String author, String image, String content){
        this.bookId = bookId;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.title = title;
        this.category = category;
        this.author = author;
        this.image = image;
        this.content = content;
    }
}
