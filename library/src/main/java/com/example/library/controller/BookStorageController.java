package com.example.library.controller;

import com.example.library.entity.BookStorage;
import com.example.library.more.StorageMore;
import com.example.library.service.BookStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookStorage")
public class BookStorageController {
    @Autowired
    private BookStorageService bookStorageService;

    @PostMapping("/updateBookStorage")
    public BookStorage updateBookStorage(@RequestBody StorageMore storageMore){
        return bookStorageService.updateBookStorage(storageMore.getBookStorageId(), storageMore.getImage(), storageMore.getImportTime(), storageMore.getQuantity());
    }
}
