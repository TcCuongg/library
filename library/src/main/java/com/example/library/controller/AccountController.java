package com.example.library.controller;

import com.example.library.entity.Account;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.more.*;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    public AccountService accountService;

    @GetMapping("/getAllAccount/{count}/{size}")
    public List<Account> getAllAccount(@PathVariable int count, @PathVariable int size){
        return accountService.getAllAccount(count, size);
    }
    @GetMapping("/findAllByRequest/{request}/{count}/{size}")
    public List<Account> findAllByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return accountService.findAllByRequest(request, count, size);
    }
    @GetMapping("/findAllMess/{count}/{size}")
    public List<Mess> findAllMess(@PathVariable int count, @PathVariable int size){
        return accountService.findAllMess(count, size);
    }
    @GetMapping("/findAllMessByRequest/{request}/{count}/{size}")
    public List<Mess> findAllMessByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return accountService.findAllMessByRequest(request, count, size);
    }



    @PutMapping("/closeAccountLowLevel")
    public List<Account> closeAccountLowLevel(){
        return accountService.closeAccountLowLevel();
    }



    @PostMapping("/Login")
    public Account login(@RequestBody Login login){
        return accountService.getByEmailAndPassword(login.getEmail(), login.getPassWord());
    }
    @PostMapping("/updateAccount")
    public Account updateAccount(@RequestBody AccountSave accountSave){
        return accountService.updateAccount(accountSave.getCardNumber(), accountSave.getName(), accountSave.getEmail(),
                accountSave.getPhone(), accountSave.getAddress(), accountSave.getLevel(), accountSave.getStatus());
    }
    @PostMapping("/addNewMess/{count}/{size}")
    public List<Mess> addNewMess(@RequestBody Send send, @PathVariable int count, @PathVariable int size){
        return accountService.addNewMess(send.getTitle(), send.getContent(), count, size);
    }
    @PostMapping("/addNewAccount")
    public Account addNewAccount(@RequestBody AccountMore accountMore){
        return accountService.addAccount(accountMore.getUsername(), accountMore.getEmail(), accountMore.getPhone(), accountMore.getAddress(), accountMore.getPassword());
    }
}
