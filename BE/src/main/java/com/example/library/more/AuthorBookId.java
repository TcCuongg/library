package com.example.library.more;

import com.example.library.entity.Author;
import com.example.library.entity.Book;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class AuthorBookId implements Serializable {
    private Long authorId;
    private Long bookId;
}
