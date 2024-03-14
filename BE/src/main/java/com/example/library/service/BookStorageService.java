package com.example.library.service;

import com.example.library.entity.BookStorage;
import com.example.library.repository.BookStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class BookStorageService {
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private RedisTemplate redisBookStorageTemplate;

    public BookStorage updateBookStorage(String bookStorageId, String image, String importTime, String quantity){
        BookStorage bookStorage = bookStorageRepository.findFirstById(Long.parseLong(bookStorageId));
        bookStorage.setImage(image);
        bookStorage.setImportTime(LocalDateTime.parse(importTime));
        bookStorage.setQuantity(Integer.parseInt(quantity));
        bookStorageRepository.save(bookStorage);

        redisBookStorageTemplate.delete("getAllBook(*");
        redisBookStorageTemplate.delete("getBookFollowDesc(*");
        redisBookStorageTemplate.delete("getBookByTitle:" + bookStorage.getBookToBookStorage().getTitle() + "(*");
        redisBookStorageTemplate.delete("getBookByCategory:" + bookStorage.getBookToBookStorage().getCategoryToBook().getName() +"(*");

        redisBookStorageTemplate.delete("findAllBookByRequest:*");

        return bookStorage;
    }
}
