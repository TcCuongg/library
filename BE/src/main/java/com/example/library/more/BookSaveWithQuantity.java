package com.example.library.more;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class BookSaveWithQuantity extends BookSave{
    private Long bookCopyId;
    private String quantity;
    public BookSaveWithQuantity(Long bookId, Long authorId, Long categoryId, Long bookCopyId, String title, String category, String author, String image, String content, String quantity) {
        super(bookId, authorId, categoryId, title, category, author, image, content);
        this.bookCopyId = bookCopyId;
        this.quantity = quantity;
    }
}
