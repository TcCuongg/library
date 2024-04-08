package com.example.library.service;

import com.example.library.entity.BookStorage;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.BookStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookStorageService {
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private RedisTemplate redisBookStorageTemplate;
    @Autowired
    private AccountRepository accountRepository;

    public List<BookStorage> updateBookStorage(String bookStorageId, String image, String quantity, Long accountId){
        List<BookStorage> storageList = new ArrayList<>();
        BookStorage bookStorage = bookStorageRepository.findFirstById(Long.parseLong(bookStorageId));
        bookStorage.setImage(image);
        bookStorage.setImportTime(LocalDateTime.now());
        bookStorage.setQuantity(Integer.parseInt(quantity));
        bookStorage.setAccountToBookStorage(accountRepository.findFirstByCardNumber(accountId));
        bookStorageRepository.save(bookStorage);
        storageList.add(bookStorageRepository.findFirstById(Long.parseLong(bookStorageId)));

        redisBookStorageTemplate.delete(redisBookStorageTemplate.keys("getBookByStorage:*"));
        redisBookStorageTemplate.delete(redisBookStorageTemplate.keys("getAllBook(*"));
        redisBookStorageTemplate.delete(redisBookStorageTemplate.keys("getBookFollowDesc(*"));
        redisBookStorageTemplate.delete(redisBookStorageTemplate.keys("getBookByTitle:" + bookStorage.getBookToBookStorage().getTitle() + "(*"));
        redisBookStorageTemplate.delete(redisBookStorageTemplate.keys("getBookByCategory:" + bookStorage.getBookToBookStorage().getCategoryToBook().getName() +"(*"));

        redisBookStorageTemplate.delete(redisBookStorageTemplate.keys("findAllBookByRequest:*"));

        return storageList;
    }

    public Long getStorageId(Long bookStorageId){
        return bookStorageRepository.findFirstById(bookStorageId).getStorageToBookStorage().getId();
    }
}
