package com.example.library.controller;

import com.example.library.entity.Storage;
import com.example.library.more.BookMore;
import com.example.library.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @GetMapping("/getAllStorage/{count}/{size}")
    public List<Storage> getAllStorage(@PathVariable int count, @PathVariable int size){
        return storageService.getAllStorage(count, size);
    }
    @GetMapping("/getStorageByRequest/{request}/{count}/{size}")
    public List<Storage> getStorageByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return storageService.getAllByRequest(request, count, size);
    }
    @GetMapping("/getBookByStorageAndRequest/{storageId}/{request}/{count}/{size}")
    public List<BookMore> getBookByStorageAndRequest(@PathVariable String storageId, @PathVariable String request, @PathVariable int count, @PathVariable int size){
        return storageService.getAllBookByStorageAndRequest(storageId, request, count, size);
    }



    @PostMapping("/addNewStorage/{count}/{size}")
    public List<Storage> addNewStorage(@RequestBody Storage storage, @PathVariable int count, @PathVariable int size){
        return storageService.addNewStorage(storage, count, size);
    }
}
