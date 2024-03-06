package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "buy")
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Account Id")
    private Account accountToBuy;

    @ManyToOne
    @JoinColumn(name = "Book Storage Id")
    private BookStorage bookStorageToBuy;

    @Column(name = "Time")
    private Timestamp time;

    @Column(name = "Status")
    private String status;

    @Column(name = "Cost")
    private Long cost;
}
