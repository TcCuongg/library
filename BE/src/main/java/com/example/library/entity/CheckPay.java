package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "checkpay")
public class CheckPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Account Id")
    private Account accountToCheckPay;

    @ManyToOne
    @JoinColumn(name = "Buy Id")
    private Buy buyToCheckPay;

    @Column(name = "Time Change")
    private LocalDateTime timeChange;
}
