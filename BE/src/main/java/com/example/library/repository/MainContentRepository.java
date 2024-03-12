package com.example.library.repository;

import com.example.library.entity.MainContent;
import com.example.library.more.Mess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MainContentRepository extends JpaRepository<MainContent, Long> {
    public MainContent findFirstById(Long id);

    public MainContent findFirstByOrderByIdDesc();
}

