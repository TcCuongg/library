package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.more.AuthorNew;
import com.example.library.more.TimeCreate;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/getAllAuthor/{count}/{size}")
    public List<Author> getAllAuthor(@PathVariable int count, @PathVariable int size){
        return authorService.getAllAuthor(count, size);
    }
    @GetMapping("/getCountAllAuthor")
    public int getCountAllAuthor(){
        return authorService.getCountAllAuthor();
    }
    @GetMapping("/getAuthorByNameOrPhone/{request}/{count}/{size}")
    public List<Author> getAuthorByNameOrPhone(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return authorService.getAuthorByNameOrPhone(request, count, size);
    }
    @GetMapping("/getCountAuthorByNameOrPhone/{request}")
    public int getCountAuthorByNameOrPhone(@PathVariable String request){
        return authorService.getCountAuthorByNameOrPhone(request);
    }


    @PostMapping("/selectionAuthorWithBirthDay/{count}/{size}")
    public List<Author> selectionAuthorWithBirthDay(@RequestBody TimeCreate timeCreate, @PathVariable int count, @PathVariable int size){
        return authorService.selectionAuthorWithBirthDay(timeCreate.getStart(), timeCreate.getEnd(), count, size);
    }
    @PostMapping("/countSelectionAuthorWithBirthDay")
    public int countSelectionAuthorWithBirthDay(@RequestBody TimeCreate timeCreate){
        return authorService.countSelectionAuthorWithBirthDay(timeCreate.getStart(), timeCreate.getEnd());
    }
    @PostMapping("/addNewAuthor")
    public List<Author> addNewAuthor(@RequestBody AuthorNew authorNew){
        return  authorService.addNewAuthor(authorNew.getName(), authorNew.getPhone(), authorNew.getDate(), authorNew.getAddress());
    }


    @PutMapping("/updateAuthor")
    public List<Author> updateAuthor(@RequestBody AuthorNew authorNew){
        return  authorService.updateAuthor(authorNew);
    }
}
