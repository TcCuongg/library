package com.example.library.entity;

import com.example.library.more.AuthorBookId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Getter
@Setter
@Table(name = "authorbook")
public class AuthorBook {
    @EmbeddedId
    private AuthorBookId id;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "Author Id")
    private Author authorToAuthorBook;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "Book Id")
    private Book bookToAuthorBook;

    public AuthorBook(){}
    public AuthorBook(AuthorBookId authorBookId, Book book, Author author){
        this.id = authorBookId;
        this.bookToAuthorBook = book;
        this.authorToAuthorBook = author;
    }
}
