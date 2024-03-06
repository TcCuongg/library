package com.example.library.repository;

import com.example.library.entity.AuthorBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBook, Long> {
    @Query("select authorBook from AuthorBook authorBook where authorBook.authorToAuthorBook.id = :authorId " +
            "and authorBook.bookToAuthorBook.id = :bookId")
    public AuthorBook findAuthorBookByAuthorIdAndBookId(@Param("authorId") Long authorId, @Param("bookId") Long bookId);
}
