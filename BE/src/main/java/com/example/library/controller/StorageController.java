package com.example.library.controller;

import com.example.library.entity.Storage;
import com.example.library.more.BookListSave;
import com.example.library.more.BookMore;
import com.example.library.more.StorageSave;
import com.example.library.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @GetMapping("/getCountAllStorage")
    public int getCountAllStorage(){
        return storageService.getCountAllStorage();
    }
    @GetMapping("/getStorageByRequest/{request}/{count}/{size}")
    public List<Storage> getStorageByRequest(@PathVariable String request, @PathVariable int count, @PathVariable int size){
        return storageService.getAllByRequest(request, count, size);
    }
    @GetMapping("/getCountAllStorageByRequest/{request}")
    public int getCountAllStorageByRequest(@PathVariable String request){
        return storageService.getCountAllStorageByRequest(request);
    }
    @GetMapping("/getBookByStorageAndRequest/{storageId}/{request}/{count}/{size}")
    public List<BookMore> getBookByStorageAndRequest(@PathVariable String storageId, @PathVariable String request, @PathVariable int count, @PathVariable int size){
        return storageService.getAllBookByStorageAndRequest(storageId, request, count, size);
    }
    @GetMapping("/getCountAllBookByStorageAndRequest/{storageId}/{request}")
    public int getCountAllBookByStorageAndRequest(@PathVariable String storageId, @PathVariable String request){
        return storageService.getCountAllBookByStorageAndRequest(storageId, request);
    }
    @GetMapping("/findStorageByStatus/{status}/{count}/{size}")
    public List<Storage> findStorageByStatus(@PathVariable String status, @PathVariable int count, @PathVariable int size){
        return storageService.findStorageByStatus(status, count, size);
    }



    @PostMapping("/addNewStorage/{count}/{size}")
    public List<Storage> addNewStorage(@RequestBody Storage storage, @PathVariable int count, @PathVariable int size){
        return storageService.addNewStorage(storage, count, size);
    }
//    @PostMapping("/addBookOnStorage/{storageId}/{accountId}")
//    public List<BookMore> addBookOnStorage(@PathVariable Long storageId, @PathVariable Long accountId, @RequestBody List<BookListSave> bookListSave){
//        return storageService.addNewBookOnStorage(storageId, accountId, bookListSave);
//    }

    @PostMapping("/addBookOnStorage/{storageId}/{accountId}/{count}/{size}")
    public List<BookMore> addBookOnStorage(@PathVariable Long storageId, @PathVariable Long accountId, @RequestBody BookListSave bookListSave, @PathVariable int count, @PathVariable int size){
        return storageService.addNewBookOnStorage(storageId, accountId, bookListSave, count, size);
    }



    @PutMapping("/updateStorage/{count}/{size}")
    public List<Storage> updateStorage(@RequestBody StorageSave storageSave, @PathVariable int count, @PathVariable int size){
        return storageService.updateStorage(Long.parseLong(storageSave.getStorageId()), storageSave.getStatus(), count, size);
    }
}
