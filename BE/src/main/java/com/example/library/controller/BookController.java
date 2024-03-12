package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.more.BookManage;
import com.example.library.more.BookMore;
import com.example.library.more.SelectBook;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/getAllBook/{count}/{size}")
    public List<BookMore> getAllBook(@PathVariable int count, @PathVariable int size){
        return bookService.getAllBook(count, size);
    }
    @GetMapping("/getTopBook")
    public BookMore getTopBook(){
        return bookService.getTopBook();
    }
    @GetMapping("/getBookFollowDesc/{count}/{size}")
    public List<BookMore> getBookFollowDesc(@PathVariable int count, @PathVariable int size){
        return bookService.getBookFollowDesc(count, size);
    }
    @GetMapping("/getBookByTitle/{count}/{size}/{bookTitle}")
    public List<BookMore> getBookByTitle(@PathVariable String bookTitle, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByTitle(bookTitle, count, size);
    }
    @GetMapping("/getBookByBookStorageId/{bookStorageId}")
    public BookMore getBookByBookStorageId(@PathVariable Long bookStorageId){
        return bookService.getBookByBookStorageId(bookStorageId);
    }
    @GetMapping("/getBookByCategory/{category}/{count}/{size}")
    public List<BookMore> getBookByCategory(@PathVariable String category, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByCategory(category, count, size);
    }
    @GetMapping("/getBookByStorage/{storageId}/{count}/{size}")
    public List<BookMore> getBookByStorage(@PathVariable Long storageId, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByStorage(storageId, count, size);
    }
    @GetMapping("/getBookByRequest/{request}/{count}/{size}")
    public List<BookMore> getBookByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return bookService.findAllByRequest(request, count, size);
    }
    @GetMapping("/getAllBookByAccountCart/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountCart(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.findAllByAccountCart(accountId, count, size);
    }
    @GetMapping("/getAllBookByAccountBuy/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountBuy(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.findAllByAccountBuy(accountId, count, size);
    }
    @GetMapping("/getAllBookManage/{count}/{size}")
    public List<BookManage> getAllBookManage(@PathVariable int count, @PathVariable int size){
        return bookService.findAllBookManage(count, size);
    }
    @GetMapping("/getBookManageByRequest/{request}/{count}/{size}")
    public List<BookManage> getBookManageByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return bookService.findAllBookManageRequest(request, count, size);
    }
    @GetMapping("/getAllStatus")
    public List<String> getAllStatus(){
        return bookService.findAllStatus();
    }



    @PostMapping("/getSelectBook/{count}/{size}")
    public List<BookManage> getSelectBook(@RequestBody SelectBook selectBook, @PathVariable int count, @PathVariable int size){
        return bookService.selectBook(selectBook.getCategory(), selectBook.getAuthor(), selectBook.getCostStart(),
                selectBook.getCostEnd(), selectBook.getStatus(), count, size);
    }
    @PostMapping("/updateBook/{count}/{size}")
    public List<BookManage> updateBook(@RequestBody BookManage bookManage, @PathVariable int count, @PathVariable int size){
        return bookService.updateBook(bookManage.getBookId(), bookManage.getAuthorId(), bookManage.getTitle(), bookManage.getCategory(),
                bookManage.getAuthor(), bookManage.getContent(), bookManage.getCost(), bookManage.getSale(),
                bookManage.getStatus(), count, size);
    }
    @PostMapping("/addNewBook")
    public BookManage addNewBook(@RequestBody BookManage bookManage){
        return bookService.addNewBook(bookManage.getTitle(), bookManage.getCategory(), bookManage.getAuthor(),
                bookManage.getContent(), bookManage.getCost().toString(), Integer.toString(bookManage.getSale()),
                bookManage.getStatus());
    }
}
