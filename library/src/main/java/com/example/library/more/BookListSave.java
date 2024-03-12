package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookListSave {
//    title:'', category:'', author:'', image:'', content:'', cost:'', quantity:''
    private String title;
    private String category;
    private String author;
    private String image;
    private String content;
    private String cost;
    private String quantity;

    public BookListSave(String title, String category, String author, String image, String content, String cost, String quantity){
        this.title = title;
        this.category = category;
        this.author = author;
        this.image = image;
        this.content = content;
        this.cost = cost;
        this.quantity = quantity;
    }
}
