package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Getter
@Setter
@Table(name = "authorbook")
public class AuthorBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Author Id")
    private Author authorToAuthorBook;

    @ManyToOne
    @JoinColumn(name = "Book Id")
    private Book bookToAuthorBook;
}
