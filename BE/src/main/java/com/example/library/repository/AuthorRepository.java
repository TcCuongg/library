package com.example.library.repository;

import com.example.library.entity.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    public Author findFirstByName(String name);

    @Query("select DISTINCT author.name from Author author")
    public List<String> findAllAuthorName();

    @Query("select author from Author author")
    public List<Author> getAllAuthor(Pageable pageable);

    @Query("select count(author.id) from Author author")
    public int getCountAllAuthor();
    public List<Author> findAllByName(String name, Pageable pageable);

    public int countAllByName(String name);

    public List<Author> findAllByPhone(Long phone, Pageable pageable);

    public int countAllByPhone(Long phone);

    @Query("select author from Author author where author.date >= :startTime and author.date <= :endTime")
    public List<Author> selectionAuthorWithBirthDay(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, Pageable pageable);

    @Query("select count(author.id) from Author author where author.date >= :startTime and author.date <= :endTime")
    public int countSelectionAuthorWithBirthDay(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    public Author findFirstByOrderByIdDesc();

    public Author findFirstById(Long id);
}
