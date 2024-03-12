package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Account Id")
    private Account accountToNotification;

    @ManyToOne
    @JoinColumn(name = "Main Content Id")
    private MainContent mainContentToNotification;

    @Column(name = "Sent")
    private LocalDateTime sent;
}
