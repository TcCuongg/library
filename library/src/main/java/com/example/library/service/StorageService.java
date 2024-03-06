package com.example.library.service;

import com.example.library.entity.Storage;
import com.example.library.more.BookMore;
import com.example.library.repository.BookRepository;
import com.example.library.repository.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;
    @Autowired
    private BookRepository bookRepository;

    public List<Storage> getAllStorage(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return storageRepository.findAllStorage(pageable);
    }
    public List<Storage> getAllByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(!storageRepository.findAllByLocation(request, pageable).isEmpty()){
            return storageRepository.findAllByLocation(request, pageable);
        }else {
            return storageRepository.findAllByPhone(Long.parseLong(request), pageable);
        }
    }
    public List<Storage> addNewStorage(Storage storage, int count, int size){
        storageRepository.save(storage);
        Pageable pageable = PageRequest.of(count, size);
        return storageRepository.findAllStorage(pageable);
    }

    public List<BookMore> getAllBookByStorageAndRequest(String storageId, String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(!bookRepository.getAllBookByStorageAndTitle(Long.parseLong(storageId), request, pageable).isEmpty()){
            return bookRepository.getAllBookByStorageAndTitle(Long.parseLong(storageId),request, pageable);
        }
        if(!bookRepository.getAllBookByStorageAndCategory(Long.parseLong(storageId), request, pageable).isEmpty()){
            return bookRepository.getAllBookByStorageAndCategory(Long.parseLong(storageId), request, pageable);
        }
        else return bookRepository.getAllBookByStorageAndAuthor(Long.parseLong(storageId), request, pageable);
    }
}
