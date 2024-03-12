package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Date")
    private LocalDateTime date;

    @Column(name = "Address")
    private String address;

    @Column(name = "Phone")
    private Long phone;

    @JsonIgnore
    @OneToMany(mappedBy = "authorToAuthorBook", cascade = CascadeType.ALL)
    private List<AuthorBook> authorBooksFromAuthor;
}
