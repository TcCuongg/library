package com.example.library.service;

import com.example.library.entity.Author;
import com.example.library.entity.AuthorBook;
import com.example.library.entity.Book;
import com.example.library.entity.Category;
import com.example.library.more.BookManage;
import com.example.library.more.BookMore;
import com.example.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorBookRepository authorBookRepository;


    public List<BookMore> getAllBook(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> list = bookRepository.getAllBook(pageable);
        Collections.reverse(list);
        return bookRepository.getAllBook(pageable);
    }
    public BookMore getTopBook(){
        return bookRepository.getTopBook();
    }
    public List<BookMore> getBookFollowDesc(int count, int size){
        Pageable pageable = PageRequest.of(count, size, Sort.by(Sort.Direction.DESC, "follow"));
        return bookRepository.getBookFollowDesc(pageable);
    }
    public List<BookMore> getBookByTitle(String bookTitle, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getBookByTitle(bookTitle, pageable);
    }
    public BookMore getBookByBookStorageId(Long bookStorageId){
        return bookRepository.getBookByBookStorageId(bookStorageId);
    }
    public List<BookMore> getBookByCategory(String category, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getBookByCategory("Đang bán", category, pageable);
    }
    public List<BookMore> getBookByStorage(Long storageId, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getBookByStorageId("Đang bán", storageId, pageable);
    }
    public List<BookMore> findAllByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(!bookRepository.getBookByCategoryOnManage(request, pageable).isEmpty()){
            return bookRepository.getBookByCategoryOnManage(request, pageable);
        }
        if(!bookRepository.getBookByAuthor(request, pageable).isEmpty()){
            return bookRepository.getBookByAuthor(request, pageable);
        }
        if(!bookRepository.getBookByContent(request, pageable).isEmpty()){
            return bookRepository.getBookByContent(request, pageable);
        }
        if(!bookRepository.getBookByStatus(request, pageable).isEmpty()){
            return bookRepository.getBookByStatus(request, pageable);
        }
        if(!bookRepository.getBookByTitleOnManage(request, pageable).isEmpty()){
            return bookRepository.getBookByTitleOnManage(request, pageable);
        }


        if(!bookRepository.getBookBySale(Integer.parseInt(request), pageable).isEmpty()){
            return bookRepository.getBookBySale(Integer.parseInt(request), pageable);
        }
        else {
            return bookRepository.getBookByCost(Long.parseLong(request), pageable);
        }
    }
    public List<BookMore> findAllByAccountCart(Long accountId, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getAllBookByAccountCart(accountId, pageable);
    }
    public List<BookMore> findAllByAccountBuy(Long accountId, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getAllBookByAccountBuy(accountId, pageable);
    }
    public List<BookManage> findAllBookManage(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getAllBookManage(pageable);
    }
    public List<BookManage> findAllBookManageRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(!bookRepository.getBookManageByCategory(request, pageable).isEmpty()){
            return bookRepository.getBookManageByCategory(request, pageable);
        }
        if(!bookRepository.getBookManageByAuthor(request, pageable).isEmpty()){
            return bookRepository.getBookManageByAuthor(request, pageable);
        }
        if(!bookRepository.getBookManageByStatus(request, pageable).isEmpty()){
            return bookRepository.getBookManageByStatus(request, pageable);
        }
        if(!bookRepository.getBookManageByTitle(request, pageable).isEmpty()){
            return bookRepository.getBookManageByTitle(request, pageable);
        }


        if(!bookRepository.getBookManageBySale(Integer.parseInt(request), pageable).isEmpty()){
            return bookRepository.getBookManageBySale(Integer.parseInt(request), pageable);
        }
        else {
            return bookRepository.getBookManageByCost(Long.parseLong(request), pageable);
        }
    }

    public List<BookManage> updateBook(Long bookId, Long authorId, String title, String categoryName, String authorName, String content, Long cost,
                                       int sale, String status, int count, int size){
        List<BookManage> bookManageList = new ArrayList<>();
        if(categoryRepository.findFirstByName(categoryName) != null && authorRepository.findFirstByName(authorName) != null){
            AuthorBook authorBook = authorBookRepository.findAuthorBookByAuthorIdAndBookId(authorId, bookId);
            authorBook.setAuthorToAuthorBook(authorRepository.findFirstByName(authorName));
            Book book = bookRepository.findFirstById(bookId);
            book.setCategoryToBook(categoryRepository.findFirstByName(categoryName));
            book.setTitle(title);
            book.setContent(content);
            book.setCost(cost);
            book.setSale(sale);
            book.setStatus(status);
            bookRepository.save(book);
            bookManageList = findAllBookManage(count, size);
            return bookManageList;
        }
        else return bookManageList;
    }
}
