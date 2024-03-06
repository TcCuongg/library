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

@Service
public class BuyService {
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Buy addNewBuy(Long bookStorageId, Long accountId, Long cost, String status){
        Timestamp timestamp = Timestamp.from(Instant.now());
        Buy buy = new Buy();
        buy.setCost(cost);
        buy.setAccountToBuy(accountRepository.findFirstByCardNumber(accountId));
        buy.setStatus(status);
        buy.setTime(timestamp);
        buy.setBookStorageToBuy(bookStorageRepository.findFirstById(bookStorageId));
        return buyRepository.save(buy);
    }
}
