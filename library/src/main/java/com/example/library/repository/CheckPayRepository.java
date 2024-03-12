package com.example.library.repository;

import com.example.library.entity.CheckPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckPayRepository extends JpaRepository<CheckPay, Long> {
}
