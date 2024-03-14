package com.example.library.service;

import com.example.library.RedisObject.BookMoreRedis;
import com.example.library.entity.*;
import com.example.library.more.BookListSave;
import com.example.library.more.BookMore;
import com.example.library.repository.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorBookRepository authorBookRepository;
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RedisTemplate redisStorageTemplate;
    @Autowired
    private RedisTemplate redisBookMoreTemplate;
    @Autowired
    private BookService bookService;

    public List<Storage> getAllStorage(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Storage> storageList = new ArrayList<>();

        String redisKey = "getAllStorage" + "(" + count + ", " + size + ")";

        boolean hasKey = redisStorageTemplate.hasKey(redisKey);

        if(hasKey){
            storageList = redisStorageTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            storageList = storageRepository.findAllStorage(pageable);
            Collections.reverse(storageList);
            if(!storageList.isEmpty()){
                redisStorageTemplate.opsForList().rightPushAll(redisKey, storageList);
            }
        }
        return storageList;
//        return storageRepository.findAllStorage(pageable);
    }
    public List<Storage> getAllByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Storage> storageList = new ArrayList<>();

        String redisKey = "getAllStorageByRequest:" + request + "(" + count + ", " + size + ")";

        boolean hasKey = redisStorageTemplate.hasKey(redisKey);

        if(!storageRepository.findAllByLocation(request, pageable).isEmpty()){
            if(hasKey){
                storageList = redisStorageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                storageList = storageRepository.findAllByLocation(request, pageable);
                Collections.reverse(storageList);
                if(!storageList.isEmpty()){
                    redisStorageTemplate.opsForList().rightPushAll(redisKey, storageList);
                }
            }
            return storageList;
//            return storageRepository.findAllByLocation(request, pageable);
        }else {
            if(hasKey){
                storageList = redisStorageTemplate.opsForList().range(redisKey, 0, -1);
            }
            else {
                storageList = storageRepository.findAllByPhone(Long.parseLong(request), pageable);
                Collections.reverse(storageList);
                if(!storageList.isEmpty()){
                    redisStorageTemplate.opsForList().rightPushAll(redisKey, storageList);
                }
            }
            return storageList;
//            return storageRepository.findAllByPhone(Long.parseLong(request), pageable);
        }
    }
    public List<BookMore> getAllBookByStorageAndRequest(String storageId, String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<BookMore> booklist = new ArrayList<>();
        List<BookMoreRedis> bookMoreRedisList = new ArrayList<>();

        String redisKey = "getAllBookByStorageAndRequest:" + storageId + request + "(" + count + ", " + size + ")";

        boolean hasKey = redisBookMoreTemplate.hasKey(redisKey);

        if(!bookRepository.getAllBookByStorageAndTitle(Long.parseLong(storageId), request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getAllBookByStorageAndTitle(Long.parseLong(storageId),request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getAllBookByStorageAndTitle(Long.parseLong(storageId),request, pageable);
        }
        if(!bookRepository.getAllBookByStorageAndCategory(Long.parseLong(storageId), request, pageable).isEmpty()){
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getAllBookByStorageAndCategory(Long.parseLong(storageId), request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getAllBookByStorageAndCategory(Long.parseLong(storageId), request, pageable);
        }
        else{
            if(hasKey){
                bookMoreRedisList = redisBookMoreTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < bookMoreRedisList.size(); i++){
                    booklist.add(new BookMore(bookMoreRedisList.get(i)));
                }
            }
            else {
                booklist = bookRepository.getAllBookByStorageAndAuthor(Long.parseLong(storageId), request, pageable);
                Collections.reverse(booklist);
                if(!booklist.isEmpty()){
                    for (int i = 0; i < booklist.size(); i++){
                        bookMoreRedisList.add(new BookMoreRedis(booklist.get(i)));
                    }
                    redisBookMoreTemplate.opsForList().rightPushAll(redisKey, bookMoreRedisList);
                }
            }
            return booklist;
//            return bookRepository.getAllBookByStorageAndAuthor(Long.parseLong(storageId), request, pageable);
        }
    }



    public List<Storage> addNewStorage(Storage storage, int count, int size){
        storageRepository.save(storage);
        Pageable pageable = PageRequest.of(count, size);
        redisStorageTemplate.delete("getAllStorage" + "(" + count + ", " + size + ")");
        redisStorageTemplate.delete("getAllStorageByRequest:" + storage.getLocation() + "(*");
        redisStorageTemplate.delete("getAllStorageByRequest:" + storage.getPhone() + "(*");
        redisStorageTemplate.delete("getAllBookByStorageAndRequest:" + storage.getId() + "*");
        return storageRepository.findAllStorage(pageable);
    }
    public List<Storage> updateStorage(Long storageId, String status, int count, int size){
        Storage storage = storageRepository.findFirstById(storageId);
        storage.setStatus(status);
        storageRepository.save(storage);
        Pageable pageable = PageRequest.of(count, size);
        redisStorageTemplate.delete("getAllBookByStorageAndRequest:" + storage.getId() + "*");
        List<Long> findAllBookStorageId = storageRepository.findAllBookStorageId();
        for(int i = 0; i < findAllBookStorageId.size(); i++){
            redisStorageTemplate.delete(redisStorageTemplate.keys("getBookByBookStorageId:" + findAllBookStorageId.get(i)));
        }
        redisStorageTemplate.delete("getBookByStorage:" + storageId);
        redisStorageTemplate.delete("getAllBook(*");
        redisStorageTemplate.delete("getBookFollowDesc(*");
        redisStorageTemplate.delete("getBookByTitle:*");
        redisStorageTemplate.delete("getBookByCategory:*");
        redisStorageTemplate.delete("findAllBookByRequest:*");
        return storageRepository.findAllStorage(pageable);
    }

    public List<BookMore> addNewBookOnStorage(Long accountId, Long storageId, List<BookListSave> bookListSaveList){
        for(int i = 0; i < bookListSaveList.size(); i++){
            Book book = bookRepository.findFirstByTitle(bookListSaveList.get(i).getTitle());
            Author author = authorRepository.findFirstByName(bookListSaveList.get(i).getAuthor());
            Category category = categoryRepository.findFirstByName(bookListSaveList.get(i).getCategory());
            Storage storage = storageRepository.findFirstById(storageId);
            Account account = accountRepository.findFirstByCardNumber(accountId);
            if(author != null && category != null){
                if(book == null){
                    Book book1 = new Book();
                    book1.setCategoryToBook(category);
                    book1.setTitle(bookListSaveList.get(i).getTitle());
                    book1.setFollow(0L);
                    book1.setCost(Long.parseLong(bookListSaveList.get(i).getCost()));
                    book1.setContent(bookListSaveList.get(i).getContent());
                    book1.setStatus("Đang bán");
                    book1.setSale(0);
                    bookRepository.save(book1);

                    book1 = bookRepository.findFirstByOrderByIdDesc();

                    AuthorBook authorBook = new AuthorBook();
                    authorBook.setAuthorToAuthorBook(author);
                    authorBook.setBookToAuthorBook(book1);
                    authorBookRepository.save(authorBook);

                    author.getAuthorBooksFromAuthor().add(authorBook);
                    author.setAuthorBooksFromAuthor(author.getAuthorBooksFromAuthor());
                    authorRepository.save(author);

                    BookStorage bookStorage = new BookStorage();
                    bookStorage.setBookToBookStorage(book1);
                    bookStorage.setStorageToBookStorage(storage);
                    bookStorage.setQuantity(Integer.parseInt(bookListSaveList.get(i).getQuantity()));
                    bookStorage.setImportTime(LocalDateTime.now());
                    bookStorage.setImage(bookListSaveList.get(i).getImage());
                    bookStorage.setAccountToBookStorage(account);
                    bookStorageRepository.save(bookStorage);

                    account.getBookStoragesFromAccount().add(bookStorage);
                    account.setBookStoragesFromAccount(account.getBookStoragesFromAccount());
                    accountRepository.save(account);

                    book1.getAuthorBooksFromBook().add(authorBook);
                    book1.setAuthorBooksFromBook(book1.getAuthorBooksFromBook());
                    book1.getBookStoragesFromBook().add(bookStorage);
                    book1.setBookStoragesFromBook(book1.getBookStoragesFromBook());
                    bookRepository.save(book1);

                    category.getBooksFromCategory().add(book1);
                    category.setBooksFromCategory(category.getBooksFromCategory());
                    categoryRepository.save(category);

                    storage.getBookStoragesFromStorage().add(bookStorage);
                    storage.setBookStoragesFromStorage(storage.getBookStoragesFromStorage());
                    storageRepository.save(storage);

                    redisStorageTemplate.delete("getAllBookByStorageAndRequest:" + storage.getId() + "*");
                    redisStorageTemplate.delete("getBookByStorage:" + storageId);
                    redisStorageTemplate.delete("getAllBook(*");
                    redisStorageTemplate.delete("getBookFollowDesc(*");
                    redisStorageTemplate.delete("getBookByTitle:*");
                    redisStorageTemplate.delete("getBookByCategory:*");
                    redisStorageTemplate.delete("findAllBookByRequest:*");
                }
                else if(getAllBookByStorageAndRequest(storageId.toString(), book.getTitle(), 0, 11).isEmpty()){
                    BookStorage bookStorage = new BookStorage();
                    bookStorage.setBookToBookStorage(book);
                    bookStorage.setStorageToBookStorage(storage);
                    bookStorage.setQuantity(Integer.parseInt(bookListSaveList.get(i).getQuantity()));
                    bookStorage.setImportTime(LocalDateTime.now());
                    bookStorage.setImage(bookListSaveList.get(i).getImage());
                    bookStorage.setAccountToBookStorage(account);
                    bookStorageRepository.save(bookStorage);

                    book.getBookStoragesFromBook().add(bookStorage);
                    book.setBookStoragesFromBook(book.getBookStoragesFromBook());
                    bookRepository.save(book);

                    storage.getBookStoragesFromStorage().add(bookStorage);
                    storage.setBookStoragesFromStorage(storage.getBookStoragesFromStorage());
                    storageRepository.save(storage);

                    redisStorageTemplate.delete("getAllBookByStorageAndRequest:" + storage.getId() + "*");
                    redisStorageTemplate.delete("getBookByStorage:" + storageId);
                    redisStorageTemplate.delete("getAllBook(*");
                    redisStorageTemplate.delete("getBookFollowDesc(*");
                    redisStorageTemplate.delete("getBookByTitle:*");
                    redisStorageTemplate.delete("getBookByCategory:*");
                    redisStorageTemplate.delete("findAllBookByRequest:*");
                }
            }
        }
        return bookService.getBookByStorage(storageId, 0, 11);
    }
}
