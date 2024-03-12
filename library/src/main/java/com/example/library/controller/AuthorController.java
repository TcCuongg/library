package com.example.library.controller;

import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/getAllAuthorName")
    public List<String> getAllAuthorName(){
        return authorService.findAllAuthorName();
    }
}
