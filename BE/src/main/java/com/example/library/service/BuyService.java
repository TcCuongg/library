package com.example.library.service;

import com.example.library.entity.*;
import com.example.library.more.BookPay;
import com.example.library.more.BuySave;
import com.example.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BuyService {
    @Autowired
    private BuyRepository buyRepository;
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RedisTemplate redisBuyTemplate;
    @Autowired
    private CheckPayRepository checkPayRepository;

    public List<BuySave> addNewBuy(Long bookStorageId, Long accountId, Long cost, String status, int quantity){
        List<BuySave> buySaveList = new ArrayList<>();
        buySaveList.add(new BuySave(bookStorageId, accountId, status, cost, quantity));
        int getCountAllBookByAccountBuy = bookRepository.getCountAllBookByAccountBuy(accountId);
        Account account = accountRepository.findFirstByCardNumber(accountId);
        BookStorage bookStorage = bookStorageRepository.findFirstById(bookStorageId);
        Buy buy = new Buy();
        buy.setCost(cost);
        buy.setAccountToBuy(account);
        buy.setStatus(status);
        buy.setTime(LocalDateTime.now());
        buy.setBookStorageToBuy(bookStorage);
        buy.setQuantity(quantity);
        buyRepository.save(buy);

        account.getBuysFromAccount().add(buy);
        account.setBuysFromAccount(account.getBuysFromAccount());
        accountRepository.save(account);

        bookStorage.getBuysFromBookStorage().add(buy);
        bookStorage.setBuysFromBookStorage(bookStorage.getBuysFromBookStorage());
        bookStorageRepository.save(bookStorage);

        redisBuyTemplate.delete("findAllBookByAccountBuy:" + accountId + "(" + getCountAllBookByAccountBuy/8 + ", " + 8 + ")");
        return buySaveList;
    }
    public BookPay updateBuy(Long accountId, Long buyId, String status){
        BookPay bookPay = bookRepository.getBookPayByBuyId(buyId);
        Buy buy = buyRepository.findFirstById(buyId);
        BookStorage bookStorage = bookStorageRepository.findFirstById(bookPay.getBookStorageId());
        if(Objects.equals(accountRepository.findFirstByCardNumber(buy.getAccountToBuy().getCardNumber()).getStatus(), "opend")){
            if(bookPay.getBuyQuantity() <= bookPay.getBookStrongQuantity()){
                buy.setStatus(status);
                buyRepository.save(buy);

                bookStorage.setQuantity(bookPay.getBookStrongQuantity() - bookPay.getBuyQuantity());
                bookStorageRepository.save(bookStorage);

                CheckPay checkPay = new CheckPay();
                checkPay.setAccountToCheckPay(accountRepository.findFirstByCardNumber(accountId));
                checkPay.setBuyToCheckPay(buy);
                checkPay.setTimeChange(LocalDateTime.now());
                checkPayRepository.save(checkPay);

                return bookRepository.getBookPayByDeliveringByBuyId(buyId);
            }else {
                buy.setStatus("Hết hàng");
                buyRepository.save(buy);
                return bookRepository.getBookPayRemainsZeroByBuyId(buyId);
            }
        }else {
            buy.setStatus("Tài khoản mua hàng đã bị khóa");
            buyRepository.save(buy);
            return bookRepository.getBookPayAccountCloseByBuyId(buyId);
        }
    }
    public List<BookPay> updateBuyOk(Long accountId, Long buyId, String status, int count, int size){
        Account account = accountRepository.findFirstByCardNumber(accountId);
        Buy buy = buyRepository.findFirstById(buyId);
        CheckPay checkPay = checkPayRepository.findFirstByBuyToCheckPay(buy);
        if(Objects.equals(status, "Đơn hàng đã giao thành công")){
            buy.setStatus(status);

            buyRepository.save(buy);

            checkPay.setTimeChange(LocalDateTime.now());
            checkPay.setAccountToCheckPay(account);
            Book book = bookRepository.findFirstById(buy.getBookStorageToBuy().getBookToBookStorage().getId());
            book.setFollow(book.getFollow() + 1);
            checkPayRepository.save(checkPay);
        }
        if(Objects.equals(status, "Đơn hàng Bom")){
            buy.setStatus(status);

            buyRepository.save(buy);

            BookStorage bookStorage = bookStorageRepository.findFirstById(buy.getBookStorageToBuy().getId());
            bookStorage.setQuantity(bookStorage.getQuantity()+buy.getQuantity());
            bookStorageRepository.save(bookStorage);

            checkPay.setTimeChange(LocalDateTime.now());
            checkPay.setAccountToCheckPay(account);

            checkPayRepository.save(checkPay);

            Account accountBuy = accountRepository.findFirstByCardNumber(buy.getAccountToBuy().getCardNumber());
            accountBuy.setLevel(accountBuy.getLevel() - 1);
            if(accountBuy.getLevel() == 0){
                accountBuy.setStatus("close");
            }
            accountRepository.save(accountBuy);
        }
        Pageable pageable = PageRequest.of(count, size);
        return bookRepository.getBookPayByDelivering(pageable);
    }
}
