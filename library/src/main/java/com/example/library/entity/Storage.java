package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collection;

@Entity
@Setter
@Getter
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Phone")
    private Long phone;
    @Column(name = "Location")
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "storageToBookStorage", cascade = CascadeType.ALL)
    private Collection<BookStorage> bookStoragesFromStorage;
}
