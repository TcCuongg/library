package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.more.*;
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
    @GetMapping("/getCountAllBook")
    public int getCountAllBook(){
        return bookService.getCountAllBook();
    }
    @GetMapping("/getTopBook")
    public BookMore getTopBook(){
        return bookService.getTopBook();
    }
    @GetMapping("/getBookFollowDesc/{count}/{size}")
    public List<BookMore> getBookFollowDesc(@PathVariable int count, @PathVariable int size){
        return bookService.getBookFollowDesc(count, size);
    }
    @GetMapping("/getCountBookFollowDesc")
    public int getCountBookFollowDesc(){
        return bookService.getCountBookFollowDesc();
    }
    @GetMapping("/getBookByTitle/{count}/{size}/{bookTitle}")
    public List<BookMore> getBookByTitle(@PathVariable String bookTitle, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByTitle(bookTitle, count, size);
    }
    @GetMapping("/getCountBookByTitle/{bookTitle}")
    public int getCountBookByTitle(@PathVariable String bookTitle){
        return bookService.getCountBookByTitle(bookTitle);
    }
    @GetMapping("/getBookByBookStorageId/{bookStorageId}")
    public BookMore getBookByBookStorageId(@PathVariable Long bookStorageId){
        return bookService.getBookByBookStorageId(bookStorageId);
    }
    @GetMapping("/getBookByCategory/{category}/{count}/{size}")
    public List<BookMore> getBookByCategory(@PathVariable String category, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByCategory(category, count, size);
    }
    @GetMapping("/getCountBookByCategory/{category}")
    public int getCountBookByCategory(@PathVariable String category){
        return bookService.getCountBookByCategory(category);
    }
    @GetMapping("/getBookByStorage/{storageId}/{count}/{size}")
    public List<BookMore> getBookByStorage(@PathVariable Long storageId, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByStorage(storageId, count, size);
    }
    @GetMapping("/getBookByStorageIdAndCategory/{storageId}/{category}/{count}/{size}")
    public List<BookMore> getBookByStorageIdAndCategory(@PathVariable Long storageId, @PathVariable String category, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByStorageIdAndCategory(storageId, category, count, size);
    }
    @GetMapping("/getCountBookByStorageIdAndCategory/{storageId}/{category}")
    public int getCountBookByStorageIdAndCategory(@PathVariable Long storageId, @PathVariable String category){
        return bookService.getCountBookByStorageIdAndCategory(storageId, category);
    }
    @GetMapping("/getBookByBookStorageOnStorage/{bookStorageId}/{count}/{size}")
    public List<BookMore> getBookByBookStorageOnStorage(@PathVariable Long bookStorageId, @PathVariable int count, @PathVariable int size){
        return bookService.getBookByBookStorageOnStorage(bookStorageId, count, size);
    }
    @GetMapping("/getBookRemainsZero/{storageId}/{count}/{size}")
    public List<BookMore> getBookRemainsZero(@PathVariable Long storageId, @PathVariable int count, @PathVariable int size){
        return bookService.getBookRemainsZero(storageId, count, size);
    }
    @GetMapping("/getCountBookRemainsZero/{storageId}")
    public int getCountBookRemainsZero(@PathVariable Long storageId){
        return bookService.getCountBookRemainsZero(storageId);
    }

    @GetMapping("/getCountBookByStorage/{storageId}")
    public int getCountBookByStorage(@PathVariable Long storageId){
        return bookService.getCountBookByStorage(storageId);
    }
    @GetMapping("/getBookByRequest/{request}/{count}/{size}")
    public List<BookMore> getBookByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return bookService.findAllByRequest(request, count, size);
    }
    @GetMapping("/getAllBookByAccountCart/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountCart(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.findAllByAccountCart(accountId, count, size);
    }
    @GetMapping("/getCountAllBookByAccountCart/{accountId}")
    public int getCountAllBookByAccountCart(@PathVariable Long accountId){
        return bookService.getCountAllBookByAccountCart(accountId);
    }
    @GetMapping("/getAllBookByAccountBuy/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountBuy(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.findAllByAccountBuy(accountId, count, size);
    }
    @GetMapping("/getAllBookByAccountBuyDelivering/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountBuyDelivering(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.getAllBookByAccountBuyDelivering(accountId, count, size);
    }
    @GetMapping("/getCountAllBookByAccountBuyDelivering/{accountId}")
    public int getCountAllBookByAccountBuyDelivering(@PathVariable Long accountId){
        return bookService.getCountAllBookByAccountBuyDelivering(accountId);
    }
    @GetMapping("/getAllBookByAccountBuyNew/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountBuyNew(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.getAllBookByAccountBuyNew(accountId, count, size);
    }
    @GetMapping("/getCountAllBookByAccountBuyNew/{accountId}")
    public int getCountAllBookByAccountBuyNew(@PathVariable Long accountId){
        return bookService.getCountAllBookByAccountBuyNew(accountId);
    }
    @GetMapping("/getAllBookByAccountBuyDelivered/{accountId}/{count}/{size}")
    public List<BookMore> getAllBookByAccountBuyDelivered(@PathVariable Long accountId, @PathVariable int count, @PathVariable  int size){
        return bookService.getAllBookByAccountBuyDelivered(accountId, count, size);
    }
    @GetMapping("/getCountAllBookByAccountBuyDelivered/{accountId}")
    public int getCountAllBookByAccountBuyDelivered(@PathVariable Long accountId){
        return bookService.getCountAllBookByAccountBuyDelivered(accountId);
    }
    @GetMapping("/getAllBookManage/{count}/{size}")
    public List<BookManage> getAllBookManage(@PathVariable int count, @PathVariable int size){
        return bookService.findAllBookManage(count, size);
    }
    @GetMapping("/getCountAllBookManage")
    public int getCountAllBookManage(){
        return bookService.getCountAllBookManage();
    }
    @GetMapping("/getBookManageByRequest/{request}/{count}/{size}")
    public List<BookManage> getBookManageByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return bookService.findAllBookManageRequest(request, count, size);
    }
    @GetMapping("/findCountAllBookManageRequest/{request}")
    public int findCountAllBookManageRequest(@PathVariable String request){
        return bookService.findCountAllBookManageRequest(request);
    }
    @GetMapping("/getAllStatus")
    public List<String> getAllStatus(){
        return bookService.findAllStatus();
    }
    @GetMapping("/getAllBookPay/{count}/{size}")
    public List<BookPay> getAllBookPay(@PathVariable int count, @PathVariable int size){
        return bookService.getAllBookPay(count, size);
    }
    @GetMapping("/getBookPayByDelivering/{count}/{size}")
    public List<BookPay> getBookPayByDelivering(@PathVariable int count, @PathVariable int size){
        return bookService.getBookPayByDelivering(count, size);
    }
    @GetMapping("/getBookPayByBom/{count}/{size}")
    public List<BookPay> getBookPayByBom(@PathVariable int count, @PathVariable int size){
        return bookService.getBookPayByBom(count, size);
    }
    @GetMapping("/getCountAllBookPay")
    public int getCountAllBookPay(){
        return bookService.getCountAllBookPay();
    }
    @GetMapping("/getCountBookPayByDelivering")
    public int getCountBookPayByDelivering(){
        return bookService.getCountBookPayByDelivering();
    }
    @GetMapping("/getCountBookPayByBom")
    public int getCountBookPayByBom(){
        return bookService.getCountBookPayByBom();
    }
    @GetMapping("/getBookPayByRequest/{request}/{option}/{count}/{size}")
    public List<BookPay> getBookPayByRequest(@PathVariable String request, @PathVariable int option, @PathVariable int count, @PathVariable int size){
        return bookService.getBookPayByRequest(request, option, count, size);
    }
    @GetMapping("/getCountBookPayByRequest/{request}/{option}")
    public int getCountBookPayByRequest(@PathVariable String request, @PathVariable int option){
        return bookService.getCountBookPayByRequest(request, option);
    }

    @GetMapping("/getBookPayByDelivered/{count}/{size}")
    public List<BookPay> getBookPayByDelivered(@PathVariable int count, @PathVariable int size){
        return bookService.getBookPayByDelivered(count, size);
    }
    @GetMapping("/getCountBookPayByDelivered")
    public int getCountBookPayByDelivered(){
        return bookService.getCountBookPayByDelivered();
    }
    @GetMapping("/getBookPayByDeliveredRequest/{request}/{count}/{size}")
    public List<BookPay> getBookPayByDeliveredRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return bookService.getBookPayByDeliveredRequest(request, count, size);
    }
    @GetMapping("/getCountBookPayByDeliveredRequest/{request}")
    public int getCountBookPayByDeliveredRequest(@PathVariable String request){
        return bookService.getCountBookPayByDeliveredRequest(request);
    }





    @PostMapping("/getSelectBook/{count}/{size}")
    public List<BookManage> getSelectBook(@RequestBody SelectBook selectBook, @PathVariable int count, @PathVariable int size){
        return bookService.selectBook(selectBook.getCategory(), selectBook.getAuthor(), selectBook.getCostStart(),
                selectBook.getCostEnd(), selectBook.getStatus(), count, size);
    }

    @PostMapping("/getCountSelectBookManage")
    public int getCountSelectBookManage(@RequestBody SelectBook selectBook){
        return bookService.getCountSelectBookManage(selectBook.getCategory(), selectBook.getAuthor(), selectBook.getCostStart(),
                selectBook.getCostEnd(), selectBook.getStatus());
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
    @PostMapping("/selectBookInStorage/{count}/{size}")
    public List<BookMore> selectBookInStorage(@PathVariable int count, @PathVariable int size, @RequestBody BookStorageSelection bookStorageSelection){
        return bookService.selectBookInStorage(bookStorageSelection.getStorageId(), bookStorageSelection.getCategory()
                , bookStorageSelection.getAuthor(), bookStorageSelection.getTimeStart(), bookStorageSelection.getTimeEnd()
                , bookStorageSelection.getQuantityStart(), bookStorageSelection.getQuantityEnd(), count, size);
    }
    @PostMapping("/countSelectBookInStorage")
    public int countSelectBookInStorage(@RequestBody BookStorageSelection bookStorageSelection){
        return bookService.countSelectBookInStorage(bookStorageSelection.getStorageId(), bookStorageSelection.getCategory()
                , bookStorageSelection.getAuthor(), bookStorageSelection.getTimeStart(), bookStorageSelection.getTimeEnd()
                , bookStorageSelection.getQuantityStart(), bookStorageSelection.getQuantityEnd());
    }
    @PostMapping("/selectionBookPayByTurnover/{count}/{size}")
    public List<BookPay> selectionBookPayByTurnover(@RequestBody BookPayTurnover bookPayTurnover, @PathVariable int count, @PathVariable int size){
        return bookService.selectionBookPayByTurnover(bookPayTurnover.getCategory(), bookPayTurnover.getTimeStart(), bookPayTurnover.getTimeEnd(), count, size);
    }
    @PostMapping("/sumBookPayByTurnover")
    public Long sumBookPayByTurnover(@RequestBody BookPayTurnover bookPayTurnover){
        return bookService.sumBookPayByTurnover(bookPayTurnover.getCategory(), bookPayTurnover.getTimeStart(), bookPayTurnover.getTimeEnd());
    }
    @PostMapping("/countSelectionBookPayByTurnover")
    public int countSelectionBookPayByTurnover(@RequestBody BookPayTurnover bookPayTurnover){
        return bookService.countSelectionBookPayByTurnover(bookPayTurnover.getCategory(), bookPayTurnover.getTimeStart(), bookPayTurnover.getTimeEnd());
    }
}
