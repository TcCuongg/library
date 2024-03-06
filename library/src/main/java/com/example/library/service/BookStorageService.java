package com.example.library.service;

import com.example.library.entity.BookStorage;
import com.example.library.repository.BookStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class BookStorageService {
    @Autowired
    private BookStorageRepository bookStorageRepository;

    public BookStorage updateBookStorage(String bookStorageId, String image, String importTime, String quantity){
        BookStorage bookStorage = bookStorageRepository.findFirstById(Long.parseLong(bookStorageId));
        bookStorage.setImage(image);
        bookStorage.setImportTime(Timestamp.valueOf(importTime));
        bookStorage.setQuantity(Integer.parseInt(quantity));
        return bookStorageRepository.save(bookStorage);
    }
}
