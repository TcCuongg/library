package com.example.library.service;

import com.example.library.RedisObject.AccountRedis;
import com.example.library.RedisObject.BookMoreRedis;
import com.example.library.entity.*;
import com.example.library.more.BookManage;
import com.example.library.more.BookMore;
import com.example.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private RedisTemplate redisBookMoreTemplate;
    @Autowired
    private RedisTemplate redisBookManageTemplate;
    @Autowired
    private RedisTemplate redisStringTemplate;


    public List<BookMore> getAllBook(int count, int size){
        Pageable pageable = PageRequest.of(bookRepository.getCountAllBook() - ((count + 1) * size), size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "getAllBook(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getAllBook(pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
    }
    public BookMore getTopBook(){
//        String redisKey = "getTopBook";
//
//        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);
//
//        if(hasKey){
//            BookMoreRedis bookMoreRedis = (BookMoreRedis) redisBookMoreTemplate.opsForValue().get(redisKey);
//            return new BookMore(bookMoreRedis);
//        }
//        else {
//            BookMore bookMore = bookRepository.getTopBook();
//            if(bookMore != null){
//                BookMoreRedis bookMoreRedis = new BookMoreRedis(bookMore);
//                redisBookMoreTemplate.opsForValue().set(redisKey, bookMoreRedis);
//            }
//            return bookMore;
//        }
        return bookRepository.getTopBook();
    }
    public List<BookMore> getBookFollowDesc(int count, int size){
        Pageable pageable = PageRequest.of(count, size, Sort.by(Sort.Direction.DESC, "follow"));
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "getBookFollowDesc(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getBookFollowDesc(pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
//        return bookRepository.getBookFollowDesc(pageable);
    }
    public List<BookMore> getBookByTitle(String bookTitle, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "getBookByTitle:" + bookTitle + "(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getBookByTitle(bookTitle, pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
//        return bookRepository.getBookByTitle(bookTitle, pageable);
    }
    public BookMore getBookByBookStorageId(Long bookStorageId){
        String redisKey = "getBookByBookStorageId:" + bookStorageId;

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            BookMoreRedis bookMoreRedis = (BookMoreRedis) redisBookMoreTemplate.opsForValue().get(redisKey);
            BookMore bookMore = new BookMore(bookMoreRedis);
            return bookMore;
        }
        else {
            BookMore bookMore = bookRepository.getBookByBookStorageId(bookStorageId);
            BookMoreRedis bookMoreRedis = new BookMoreRedis(bookMore);
            redisBookMoreTemplate.opsForValue().set(redisKey, bookMoreRedis);
            return bookMore;
        }
//        return bookRepository.getBookByBookStorageId(bookStorageId);
    }
    public List<BookMore> getBookByCategory(String category, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "getBookByCategory:" + category +"(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getBookByCategory("Đang bán", category, pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
//        return bookRepository.getBookByCategory("Đang bán", category, pageable);
    }
    public List<BookMore> getBookByStorage(Long storageId, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "getBookByStorage:" + storageId +"(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getBookByStorageId(storageId, pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
//        return bookRepository.getBookByStorageId(storageId, pageable);
    }
    public List<BookMore> findAllByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "findAllBookByRequest:" + request +"(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(!bookRepository.getBookByCategory("Đang bán", request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookByCategory("Đang bán", request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookByCategory("Đang bán", request, pageable);
        }
        if(!bookRepository.getBookByAuthor(request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookByAuthor(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookByAuthor(request, pageable);
        }
        if(!bookRepository.getBookByContent(request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookByContent(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookByContent(request, pageable);
        }
        if(!bookRepository.getBookByStatus(request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookByStatus(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookByStatus(request, pageable);
        }
        if(!bookRepository.getBookByTitle(request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookByTitle(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookByTitle(request, pageable);
        }


        if(!bookRepository.getBookBySale(Integer.parseInt(request), pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookBySale(Integer.parseInt(request), pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookBySale(Integer.parseInt(request), pageable);
        }
        else {
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getBookByCost(Long.parseLong(request), pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getBookByCost(Long.parseLong(request), pageable);
        }
    }



    public List<BookMore> findAllByAccountCart(Long accountId, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "findAllBookByAccountCart:" + accountId +"(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getAllBookByAccountCart(accountId, pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
//        return bookRepository.getAllBookByAccountCart(accountId, pageable);
    }
    public List<BookMore> findAllByAccountBuy(Long accountId, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "findAllBookByAccountBuy:" + accountId + "(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(hasKey){
            bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < bookMoreRedisList.size(); i++){
                booklist.add(new BookMore(bookMoreRedisList.get(i)));
            }
        }
        else {
            booklist = bookRepository.getAllBookByAccountBuy(accountId, pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                for (int i = 0; i < booklist.size(); i++){
                    bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                }
                redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
            }
        }
        return booklist;
//        return bookRepository.getAllBookByAccountBuy(accountId, pageable);
    }


    public List<BookManage> findAllBookManage(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookManage> booklist = new ArrayList<>();

        String redisKey = "findAllBookManage(" + count + ", " + size + ")";

        boolean hasKey = redisBookManageTemplate.hasKey(redisKey);

        if(hasKey){
            booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            booklist = bookRepository.getAllBookManage(pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
            }
        }
        return booklist;
//        return bookRepository.getAllBookManage(pageable);
    }
    public List<BookManage> findAllBookManageRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookManage> booklist = new ArrayList<>();

        String redisKey = "findAllBookManageRequest:" + request + "(" + count + ", " + size + ")";

        boolean hasKey = redisBookManageTemplate.hasKey(redisKey);

        if(!bookRepository.getBookManageByCategory(request, pageable).isEmpty()){
            if(hasKey){
                booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                booklist = bookRepository.getBookManageByCategory(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
                }
            }
            return booklist;
//            return bookRepository.getBookManageByCategory(request, pageable);
        }
        if(!bookRepository.getBookManageByAuthor(request, pageable).isEmpty()){
            if(hasKey){
                booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                booklist = bookRepository.getBookManageByAuthor(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
                }
            }
            return booklist;
//            return bookRepository.getBookManageByAuthor(request, pageable);
        }
        if(!bookRepository.getBookManageByStatus(request, pageable).isEmpty()){
            if(hasKey){
                booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                booklist = bookRepository.getBookManageByStatus(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
                }
            }
            return booklist;
//            return bookRepository.getBookManageByStatus(request, pageable);
        }
        if(!bookRepository.getBookManageByTitle(request, pageable).isEmpty()){
            if(hasKey){
                booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                booklist = bookRepository.getBookManageByTitle(request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
                }
            }
            return booklist;
//            return bookRepository.getBookManageByTitle(request, pageable);
        }


        if(!bookRepository.getBookManageBySale(Integer.parseInt(request), pageable).isEmpty()){
            if(hasKey){
                booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                booklist = bookRepository.getBookManageBySale(Integer.parseInt(request), pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
                }
            }
            return booklist;
//            return bookRepository.getBookManageBySale(Integer.parseInt(request), pageable);
        }
        else {
            if(hasKey){
                booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                booklist = bookRepository.getBookManageByCost(Long.parseLong(request), pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
                }
            }
            return booklist;
//            return bookRepository.getBookManageByCost(Long.parseLong(request), pageable);
        }
    }
    public List<BookManage> selectBook(String category, String author, String costStart, String costEnd, String status, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookManage> booklist = new ArrayList<>();

        String redisKey = "selectBook:" + category + "  " + author + "  " + costStart + "  " + costEnd + "  " + status + "(" + count + ", " + size + ")";

        boolean hasKey = redisBookManageTemplate.hasKey(redisKey);

        Long start, end;
        if(costStart.isEmpty()){
           start = 0L;
        }
        else start = Long.parseLong(costStart);
        if(costEnd.isEmpty()){
            end = 999999999999999999L;
        }else end = Long.parseLong(costEnd);
        if(category == ""){
            category = null;
        }
        if(author == ""){
            author = null;
        }
        if(status == ""){
            status = null;
        }

        if(hasKey){
            booklist = redisBookManageTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            booklist = bookRepository.selectBook(category, author, start, end, status, pageable);
            Collections.reverse(booklist);
            if(!booklist.isEmpty()){
                redisBookManageTemplate.opsForList().rightPushAll(redisKey, booklist);
            }
        }
        return booklist;
//        return bookRepository.selectBook(category, author, start, end, status, pageable);
    }
    public List<String> findAllStatus(){
        List<String> stringList = new ArrayList<>();

        String redisKey = "findAllBookStatus";

        boolean hasKey = redisStringTemplate.hasKey(redisKey);

        if(hasKey){
            stringList = redisStringTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            stringList = bookRepository.findAllStatus();
            if(!stringList.isEmpty()){
                redisStringTemplate.opsForList().rightPushAll(redisKey, stringList);
            }
        }
        return stringList;
//        return bookRepository.findAllStatus();
    }





    public BookManage addNewBook(String title, String category, String author, String content, String cost, String sale, String status){
        int getCountBookByTitle = bookRepository.getCountBookByTitle(title);
        int getCountBookByCategory = bookRepository.getCountBookByCategory(category);
        int getCountAllBookManage = bookRepository.getCountAllBookManage();
        int countSelectBook = bookRepository.countSelectBook(category, author, status);
        Book book = new Book();
        AuthorBook authorBook = new AuthorBook();
        if(categoryRepository.findFirstByName(category) != null && authorRepository.findFirstByName(author) != null){
            Category category1 = categoryRepository.findFirstByName(category);
            Author author1 = authorRepository.findFirstByName(author);

            book.setTitle(title);
            book.setFollow(0L);
            book.setCategoryToBook(category1);
            book.setCost(Long.parseLong(cost));
            book.setContent(content);
            book.setSale(Integer.parseInt(sale));
            book.setStatus(status);

            bookRepository.save(book);
            book = bookRepository.findFirstByOrderByIdDesc();

            authorBook.setBookToAuthorBook(book);
            authorBook.setAuthorToAuthorBook(author1);
            authorBookRepository.save(authorBook);

            author1.getAuthorBooksFromAuthor().add(authorBook);
            author1.setAuthorBooksFromAuthor(author1.getAuthorBooksFromAuthor());
            authorRepository.save(author1);
//            redisBookManageTemplate.delete("getAllBook(" + (bookRepository.getCountAllBook() - 11) + ", " + 11 + ")");
//            redisBookManageTemplate.delete("getBookFollowDesc(" + bookRepository.getCountAllBook()/11 + ", " + 11 + ")");
//            redisBookManageTemplate.delete("getBookByTitle:" + title + "(" + getCountBookByTitle/8 + ", " + 8 + ")");
//            redisBookManageTemplate.delete("getBookByCategory:" + category +"(" + getCountBookByCategory/8 + ", " + 8 + ")");
//            redisBookManageTemplate.delete("findAllBookManage(" + getCountAllBookManage/11 + ", " + 11 + ")");
//            redisBookManageTemplate.delete("selectBook:" + category + "  " + author + "  *  *  " + status + "(" + countSelectBook/11 + ", " + 11 + ")");
//
//            redisBookManageTemplate.delete("findAllBookByRequest:" + category +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + author +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + content +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + status +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + title +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + sale +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + cost +"(*");

            redisBookManageTemplate.delete("findAllBookManageRequest:" + category +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + author +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + status +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + title +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + sale +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + cost +"(*");
        }
        return bookRepository.getBookManageByBookId(book.getId());
    }
    public List<BookManage> updateBook(Long bookId, Long authorId, String title, String categoryName, String authorName, String content, Long cost,
                                       int sale, String status, int count, int size){
        int getCountBookByTitle = bookRepository.getCountBookByTitle(title);
        int getCountBookByCategory = bookRepository.getCountBookByCategory(categoryName);
        int getCountAllBookManage = bookRepository.getCountAllBookManage();
        int countSelectBook = bookRepository.countSelectBook(categoryName, authorName, status);
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
//            redisBookManageTemplate.delete("getAllBook(*");
//            redisBookManageTemplate.delete("getBookFollowDesc(*");
//            redisBookManageTemplate.delete("getBookByTitle:" + title + "(*");
//            redisBookManageTemplate.delete("getBookByCategory:" + categoryName +"(*");
//            redisBookManageTemplate.delete("findAllBookManage(*");
//            redisBookManageTemplate.delete("selectBook:" + categoryName + "  " + authorName + "  *  *  " + status + "(*");
//
//            redisBookManageTemplate.delete("findAllBookByRequest:" + categoryName +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + authorName +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + content +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + status +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + title +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + sale +"(*");
//            redisBookManageTemplate.delete("findAllBookByRequest:" + cost +"(*");

            redisBookManageTemplate.delete("findAllBookManageRequest:" + categoryName +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + authorName +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + status +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + title +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + sale +"(*");
            redisBookManageTemplate.delete("findAllBookManageRequest:" + cost +"(*");
            return bookManageList;
        }
        else return bookManageList;
    }
}
