package com.example.library.service;

import com.example.library.entity.Account;
import com.example.library.entity.BookStorage;
import com.example.library.entity.Buy;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.BookStorageRepository;
import com.example.library.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class BuyService {
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Buy addNewBuy(Long bookStorageId, Long accountId, Long cost, String status){
        Account account = accountRepository.findFirstByCardNumber(accountId);
        BookStorage bookStorage = bookStorageRepository.findFirstById(bookStorageId);
        Buy buy = new Buy();
        buy.setCost(cost);
        buy.setAccountToBuy(account);
        buy.setStatus(status);
        buy.setTime(LocalDateTime.now());
        buy.setBookStorageToBuy(bookStorage);

        account.getBuysFromAccount().add(buy);
        account.setBuysFromAccount(account.getBuysFromAccount());
        accountRepository.save(account);

        bookStorage.getBuysFromBookStorage().add(buy);
        bookStorage.setBuysFromBookStorage(bookStorage.getBuysFromBookStorage());
        bookStorageRepository.save(bookStorage);

        return buyRepository.save(buy);
    }
}