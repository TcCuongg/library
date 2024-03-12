package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "bookstorage")
public class BookStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Book Id")
    private Book bookToBookStorage;

    @ManyToOne
    @JoinColumn(name = "Storage Id")
    private Storage storageToBookStorage;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Import Time")
    private LocalDateTime importTime;

    @Column(name = "Image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "Account Import Id")
    private Account accountToBookStorage;

    @JsonIgnore
    @OneToMany(mappedBy = "bookStorageToCart", cascade = CascadeType.ALL)
    private List<Cart> cartsFromBookStorage;

    @JsonIgnore
    @OneToMany(mappedBy = "bookStorageToBuy", cascade = CascadeType.ALL)
    private List<Buy> buysFromBookStorage;
}
