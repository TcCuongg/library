package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "Category Id")
    private Category categoryToBook;

    @Column(name = "Follow")
    private Long follow;

    @Column(name = "Cost")
    private Long cost;

    @Column(name = "Content")
    private String content;

    @Column(name = "Status")
    private String status;

    @Column(name = "Sale")
    private int sale;

    @JsonIgnore
    @OneToMany(mappedBy = "bookToBookStorage", cascade = CascadeType.ALL)
    private List<BookStorage> bookStoragesFromBook;

    @JsonIgnore
    @OneToMany(mappedBy = "bookToAuthorBook", cascade = CascadeType.ALL)
    private List<AuthorBook> authorBooksFromBook;
}
