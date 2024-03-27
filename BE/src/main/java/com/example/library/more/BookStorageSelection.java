package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookStorageSelection {
    private Long storageId;
    private String category;
    private String author;
    private String timeStart;
    private String timeEnd;
    private String quantityStart;
    private String quantityEnd;

    public BookStorageSelection (){}
    public BookStorageSelection(Long storageId, String category, String author, String timeStart, String timeEnd, String quantityStart, String quantityEnd){
        this.storageId = storageId;
        this.category = category;
        this.author = author;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.quantityStart = quantityStart;
        this.quantityEnd = quantityEnd;
    }
}
