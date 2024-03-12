package com.example.library.service;

import com.example.library.repository.CheckPayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckPayService {
    @Autowired
    private CheckPayRepository checkPayRepository;
}
