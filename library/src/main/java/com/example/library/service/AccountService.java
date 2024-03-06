package com.example.library.service;

import com.example.library.entity.Account;
import com.example.library.entity.MainContent;
import com.example.library.entity.Notification;
import com.example.library.more.Mess;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.MainContentRepository;
import com.example.library.repository.NotificationRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MainContentRepository mainContentRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Account> getAllAccount(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return accountRepository.getAllAccount(pageable);
    }
    public List<Account> findAllByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if (!accountRepository.findAllByAddress(request, pageable).isEmpty()) {
            return accountRepository.findAllByAddress(request, pageable);
        }
        if (!accountRepository.findAllByEmail(request, pageable).isEmpty()) {
            return accountRepository.findAllByEmail(request, pageable);
        }
        if (!accountRepository.findAllByName(request, pageable).isEmpty()) {
            return accountRepository.findAllByName(request, pageable);
        }
        if (!accountRepository.findAllByStatus(request, pageable).isEmpty()) {
            return accountRepository.findAllByStatus(request, pageable);
        }
        if (!accountRepository.findAllByLevel(Integer.parseInt(request), pageable).isEmpty()) {
            return accountRepository.findAllByLevel(Integer.parseInt(request), pageable);
        }
        return accountRepository.findAllByPhone(Long.parseLong(request), pageable);
    }
    public Account getByEmailAndPassword(String email, String password){
        return accountRepository.findByEmailAndPassword(email, password);
    }
    public List<Account> closeAccountLowLevel(){
        List<Account> accounts = accountRepository.findAllByLowLevel();
        for(int i = 0; i < accounts.size(); i++)
        {
            accounts.get(i).setStatus("close");
        }
        return accountRepository.saveAll(accounts);
    }
    public Account updateAccount(Long cardNumber, String name, String email, String phone,
                                 String address, String level, String status){
        Account account = accountRepository.findFirstByCardNumber(cardNumber);
        account.setName(name);
        account.setEmail(email);
        account.setPhone(Long.parseLong(phone));
        account.setAddress(address);
        account.setLevel(Integer.parseInt(level));
        account.setStatus(status);
        return accountRepository.save(account);
    }
    public List<Mess> findAllMess(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return accountRepository.findAllMess(pageable);
    }
    public List<Mess> findAllMessByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(!accountRepository.findAllMessByEmail(request, pageable).isEmpty()){
            return accountRepository.findAllMessByEmail(request, pageable);
        }
        if(!accountRepository.findAllMessByContent(request, pageable).isEmpty()){
            return accountRepository.findAllMessByContent(request, pageable);
        }
        else return accountRepository.findAllMessByType(request, pageable);
    }
    public List<Mess> addNewMess(String title, String content, int count, int size){
        MainContent mainContent = new MainContent();
        List<Account> accountList = accountRepository.findAllAccount();
        mainContent.setType(title);
        mainContent.setContent(content);
        mainContentRepository.save(mainContent);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Notification> notificationList = new ArrayList<>();
        for(int i = 0; i < accountList.size(); i++){
            Notification notification = new Notification();
            notification.setSent(timestamp);
            notification.setAccountToNotification(accountList.get(i));
            notification.setMainContentToNotification(mainContent);
            notificationList.add(notification);
            notificationRepository.save(notification);
        }
        return findAllMess(count, size);
    }
    public Account addAccount(String username, String email, String phone, String address, String password) {
        Account account = new Account();
        account.setName(username);
        account.setEmail(email);
        account.setAddress(address);
        account.setPassword(sha256(password));
        account.setPhone(Long.parseLong(phone));
        account.setAvatar("root");
        account.setLevel(10);
        account.setStatus("opend");
        account.setType("user");
        return accountRepository.save(account);
    }


    private String sha256(String password) {
//        try{
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
//            BigInteger number = new BigInteger(1, hash);
//            StringBuilder hexString = new StringBuilder(number.toString(16));
//            while (hexString.length() < 32) {
//                hexString.insert(0, '0');
//            }
//            return hexString.toString();
//        } catch(Exception ex){
//            throw new RuntimeException(ex);
//        }
        String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }

}
