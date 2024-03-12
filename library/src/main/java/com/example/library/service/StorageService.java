package com.example.library.service;

import com.example.library.entity.*;
import com.example.library.more.BookListSave;
import com.example.library.more.BookMore;
import com.example.library.repository.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<Storage> updateBook(Long storageId, String status, int count, int size){
        Storage storage = storageRepository.findFirstById(storageId);
        storage.setStatus(status);
        storageRepository.save(storage);
        Pageable pageable = PageRequest.of(count, size);
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
                }
            }
        }
        return getAllBookByStorageAndRequest("1","Truyện tranh", 0, 8);
    }
}
