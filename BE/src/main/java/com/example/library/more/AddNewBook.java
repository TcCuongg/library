package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddNewBook {
    private Long storageId;
    private String bookTitle;
    private String authorName;
    private String categoryName;
    private String image;
    private String content;
    private String cost;
    private String quantity;
    public AddNewBook(Long storageId, String bookTitle, String authorName, String categoryName, String image,
                      String content, String cost, String quantity){
        this.storageId = storageId;
        this.bookTitle = bookTitle;
        this.authorName = authorName;
        this.categoryName = categoryName;
        this.image = image;
        this.content = content;
        this.cost = cost;
        this.quantity = quantity;
    }
}
