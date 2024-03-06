package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "maincontent")
public class MainContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Type")
    private String type;
    @Column(name = "Content")
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "mainContentToNotification", cascade = CascadeType.ALL)
    private List<Notification> notificationsFromMainContent;
}
