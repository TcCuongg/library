package com.example.library.service;

import com.example.library.RedisObject.MessRedis;
import com.example.library.entity.Account;
import com.example.library.entity.MainContent;
import com.example.library.entity.Notification;
import com.example.library.RedisObject.AccountRedis;
import com.example.library.more.Mess;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.MainContentRepository;
import com.example.library.repository.NotificationRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
    @Autowired
    private RedisTemplate redisAccountRedisTemplate;
    @Autowired
    private RedisTemplate redisMessTemplate;
    @Autowired
    private RedisTemplate redisStringTemplate;


    public List<Account> getAllAccount(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Account> accountList = new ArrayList<>();
        List<AccountRedis> accountRedisList = new ArrayList<>();

        String redisKey = "getAllAccount(" + count + ", " + size + ")";

        boolean hasKey = redisAccountRedisTemplate.hasKey(redisKey);

        if(hasKey){
            accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < accountRedisList.size(); i++){
                accountList.add(new Account(accountRedisList.get(i)));
            }
        }
        else {
            accountList = accountRepository.getAllAccount(pageable);
            if(!accountList.isEmpty()){
                for (int i = 0; i < accountList.size(); i++){
                    accountRedisList.add(new AccountRedis(accountList.get(i)));
                }
                redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
            }
        }
        return accountList;
    }
    public List<Account> findAllByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Account> accountList = new ArrayList<>();
        List<AccountRedis> accountRedisList = new ArrayList<>();

        String redisKey = "findAllByRequest:" + request + "(" + count + ", " + size + ")";

        boolean hasKey = redisAccountRedisTemplate.hasKey(redisKey);

        if (!accountRepository.findAllByAddress(request, pageable).isEmpty()) {
            if(hasKey){
                accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < accountRedisList.size(); i++){
                    accountList.add(new Account(accountRedisList.get(i)));
                }
            }
            else {
                accountList = accountRepository.findAllByAddress(request, pageable);
                if(!accountList.isEmpty()){
                    for (int i = 0; i < accountList.size(); i++){
                        accountRedisList.add(new AccountRedis(accountList.get(i)));
                    }
                    redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
                }
            }
            return accountList;
        }
        if (!accountRepository.findAllByEmail(request, pageable).isEmpty()) {
            if(hasKey){
                accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < accountRedisList.size(); i++){
                    accountList.add(new Account(accountRedisList.get(i)));
                }
            }
            else {
                accountList = accountRepository.findAllByEmail(request, pageable);
                if(!accountList.isEmpty()){
                    for (int i = 0; i < accountList.size(); i++){
                        accountRedisList.add(new AccountRedis(accountList.get(i)));
                    }
                    redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
                }
            }
            return accountList;
        }
        if (!accountRepository.findAllByName(request, pageable).isEmpty()) {
            if(hasKey){
                accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < accountRedisList.size(); i++){
                    accountList.add(new Account(accountRedisList.get(i)));
                }
            }
            else {
                accountList = accountRepository.findAllByName(request, pageable);
                if(!accountList.isEmpty()){
                    for (int i = 0; i < accountList.size(); i++){
                        accountRedisList.add(new AccountRedis(accountList.get(i)));
                    }
                    redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
                }
            }
            return accountList;
        }
        if (!accountRepository.findAllByStatus(request, pageable).isEmpty()) {
            if(hasKey){
                accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < accountRedisList.size(); i++){
                    accountList.add(new Account(accountRedisList.get(i)));
                }
            }
            else {
                accountList = accountRepository.findAllByStatus(request, pageable);
                if(!accountList.isEmpty()){
                    for (int i = 0; i < accountList.size(); i++){
                        accountRedisList.add(new AccountRedis(accountList.get(i)));
                    }
                    redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
                }
            }
            return accountList;
        }
        if (!accountRepository.findAllByLevel(Integer.parseInt(request), pageable).isEmpty()) {
            if(hasKey){
                accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < accountRedisList.size(); i++){
                    accountList.add(new Account(accountRedisList.get(i)));
                }
            }
            else {
                accountList = accountRepository.findAllByLevel(Integer.parseInt(request), pageable);
                if(!accountList.isEmpty()){
                    for (int i = 0; i < accountList.size(); i++){
                        accountRedisList.add(new AccountRedis(accountList.get(i)));
                    }
                    redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
                }
            }
            return accountList;
        }else{
            if(hasKey){
                accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < accountRedisList.size(); i++){
                    accountList.add(new Account(accountRedisList.get(i)));
                }
            }
            else {
                accountList = accountRepository.findAllByPhone(Long.parseLong(request), pageable);
                if(!accountList.isEmpty()){
                    for (int i = 0; i < accountList.size(); i++){
                        accountRedisList.add(new AccountRedis(accountList.get(i)));
                    }
                    redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
                }
            }
            return accountList;
        }
    }
    public Account getByEmailAndPassword(String email, String password){
        String redisKey = "getByEmailAndPassword:" + email;

        boolean hasKey = redisAccountRedisTemplate.hasKey(redisKey);
        if(hasKey){
            AccountRedis accountRedis = (AccountRedis) redisAccountRedisTemplate.opsForValue().get(redisKey);
            Account account = new Account(accountRedis);
            return account;
        }
        else {
            Account account = accountRepository.findByEmailAndPassword(email, password);
            if(account != null){
                AccountRedis accountRedis = new AccountRedis(account);
                redisAccountRedisTemplate.opsForValue().set(redisKey, accountRedis);
            }
            return account;
        }
    }
    public List<Mess> findAllMess(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Mess> messList = new ArrayList<>();
        List<MessRedis> messRedisList = new ArrayList<>();

        String redisKey = "findAllMess(" + count + ", " + size + ")";

        boolean hasKey = redisMessTemplate.hasKey(redisKey);

        if(hasKey){
            messRedisList = redisMessTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < messRedisList.size(); i++){
                messList.add(new Mess(messRedisList.get(i)));
            }
        }
        else {
            messList = accountRepository.findAllMess(pageable);
            if(!messList.isEmpty()){
                for (int i = 0; i < messList.size(); i++){
                    messRedisList.add(new MessRedis(messList.get(i)));
                }
                redisMessTemplate.opsForList().rightPushAll(redisKey, messRedisList);
            }
        }
        return messList;
    }
    public List<Mess> findAllMessByRequest(String request, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Mess> messList = new ArrayList<>();
        List<MessRedis> messRedisList = new ArrayList<>();

        String redisKey = "findAllMessByRequest: " + request + "(" + count + ", " + size + ")";

        boolean hasKey = redisMessTemplate.hasKey(redisKey);

        if(!accountRepository.findAllMessByEmail(request, pageable).isEmpty()){
            if(hasKey){
                messRedisList = redisMessTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < messRedisList.size(); i++){
                    messList.add(new Mess(messRedisList.get(i)));
                }
            }
            else {
                messList = accountRepository.findAllMessByEmail(request, pageable);
                if(!messList.isEmpty()){
                    for (int i = 0; i < messList.size(); i++){
                        messRedisList.add(new MessRedis(messList.get(i)));
                    }
                    redisMessTemplate.opsForList().rightPushAll(redisKey, messRedisList);
                }
            }
            return messList;
        }
        if(!accountRepository.findAllMessByContent(request, pageable).isEmpty()){
            if(hasKey){
                messRedisList = redisMessTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < messRedisList.size(); i++){
                    messList.add(new Mess(messRedisList.get(i)));
                }
            }
            else {
                messList = accountRepository.findAllMessByContent(request, pageable);
                if(!messList.isEmpty()){
                    for (int i = 0; i < messList.size(); i++){
                        messRedisList.add(new MessRedis(messList.get(i)));
                    }
                    redisMessTemplate.opsForList().rightPushAll(redisKey, messRedisList);
                }
            }
            return messList;
        }
        else{
            if(hasKey){
                messRedisList = redisMessTemplate.opsForList().range(redisKey, 0, -1);
                for(int i = 0; i < messRedisList.size(); i++){
                    messList.add(new Mess(messRedisList.get(i)));
                }
            }
            else {
                messList = accountRepository.findAllMessByType(request, pageable);
                if(!messList.isEmpty()){
                    for (int i = 0; i < messList.size(); i++){
                        messRedisList.add(new MessRedis(messList.get(i)));
                    }
                    redisMessTemplate.opsForList().rightPushAll(redisKey, messRedisList);
                }
            }
            return messList;
        }
    }

    public List<Account> findAccountByTimeCreate(String timeStart, String timeEnd, String status, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(timeStart == "") timeStart = LocalDateTime.MIN.toString();
        else if(!timeStart.contains("T") && !timeStart.contains(" ")){
            timeStart += "T00:00:00";
        } else{
            timeStart = timeStart.replace(" ", "T");
        }
        if(timeEnd == "") timeEnd = "9999-12-31T23:59:59";
        else if(!timeEnd.contains("T") && !timeEnd.contains(" ")){
            timeEnd += "T00:00:00";
        } else{
            timeEnd = timeEnd.replace(" ", "T");
        }
        if(status == "") status = null;List<Account> accountList = new ArrayList<>();
        List<AccountRedis> accountRedisList = new ArrayList<>();

        String redisKey = "findAccountByTimeCreate:" + timeStart + "  " + timeEnd + "  " + status + "(" + count + ", " + size + ")";

        boolean hasKey = redisAccountRedisTemplate.hasKey(redisKey);

        if(hasKey){
            accountRedisList = redisAccountRedisTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < accountRedisList.size(); i++){
                accountList.add(new Account(accountRedisList.get(i)));
            }
        }
        else {
            accountList = accountRepository.selectAccount(LocalDateTime.parse(timeStart), LocalDateTime.parse(timeEnd), status, pageable);
            if(!accountList.isEmpty()){
                for (int i = 0; i < accountList.size(); i++){
                    accountRedisList.add(new AccountRedis(accountList.get(i)));
                }
                redisAccountRedisTemplate.opsForList().rightPushAll(redisKey, accountRedisList);
            }
        }
        return accountList;
    }

    public List<Mess> findMessByTimeSent(String timeStart, String timeEnd, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        if(timeStart == "") timeStart = LocalDateTime.MIN.toString();
        else if(!timeStart.contains("T") && !timeStart.contains(" ")){
            timeStart += "T00:00:00";
        } else{
            timeStart = timeStart.replace(" ", "T");
        }
        if(timeEnd == "") timeEnd = "9999-12-31T23:59:59";
        else if(!timeEnd.contains("T") && !timeEnd.contains(" ")){
            timeEnd += "T00:00:00";
        } else{
            timeEnd = timeEnd.replace(" ", "T");
        }
        List<Mess> messList = new ArrayList<>();
        List<MessRedis> messRedisList = new ArrayList<>();

        String redisKey = "findMessByTimeSent:" + timeStart + "  " + timeEnd + "(" + count + ", " + size + ")";

        boolean hasKey = redisMessTemplate.hasKey(redisKey);

        if(hasKey){
            messRedisList = redisMessTemplate.opsForList().range(redisKey, 0, -1);
            for(int i = 0; i < messRedisList.size(); i++){
                messList.add(new Mess(messRedisList.get(i)));
            }
        }
        else {
            messList = accountRepository.findAllMessBySent(LocalDateTime.parse(timeStart), LocalDateTime.parse(timeEnd), pageable);
            if(!messList.isEmpty()){
                for (int i = 0; i < messList.size(); i++){
                    messRedisList.add(new MessRedis(messList.get(i)));
                }
                redisMessTemplate.opsForList().rightPushAll(redisKey, messRedisList);
            }
        }
        return messList;
    }

    public List<String> findAllAccountStatus(){
        List<String> stringList = new ArrayList<>();

        String redisKey = "findAllAccountStatus";

        boolean hasKey = redisStringTemplate.hasKey(redisKey);

        if(hasKey){
            stringList = redisStringTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            stringList = accountRepository.findAllAccountStatus();
            if(!stringList.isEmpty()){
                redisStringTemplate.opsForList().rightPushAll(redisKey, stringList);
            }
        }
        return stringList;
    }





    public Account addAccount(String username, String email, String phone, String address, String password, String type) {
        Account account = new Account();
        if(accountRepository.findAllByEmail(email).isEmpty() && accountRepository.findAllByPhone(Long.parseLong(phone)).isEmpty()){
            account.setName(username);
            account.setEmail(email);
            account.setAddress(address);
            account.setPassword(sha256(password));
            account.setPhone(Long.parseLong(phone));
            account.setAvatar("root");
            account.setLevel(10);
            account.setStatus("opend");
            account.setType(type);

            account.setTimeCreate(LocalDateTime.now());
        }
        return accountRepository.save(account);
    }

    public List<Account> closeAccountLowLevel(){
        List<Account> accounts = accountRepository.findAllByLowLevel();
        for(int i = 0; i < accounts.size(); i++)
        {
            accounts.get(i).setStatus("close");
        }
        return accountRepository.saveAll(accounts);
    }
    public List<Account> updateAccount(Long cardNumber, String name, String email, String phone,
                                       String address, String level, String status, int count, int size){
        Account account = accountRepository.findFirstByCardNumber(cardNumber);
        account.setName(name);
        account.setEmail(email);
        account.setPhone(Long.parseLong(phone));
        account.setAddress(address);
        account.setLevel(Integer.parseInt(level));
        account.setStatus(status);
        accountRepository.save(account);
        Pageable pageable = PageRequest.of(count, size);
        return accountRepository.getAllAccount(pageable);
    }
    public List<Mess> addNewMess(String title, String content, int count, int size){
        MainContent mainContent = new MainContent();
        List<Account> accountList = accountRepository.findAllAccount();
        mainContent.setType(title);
        mainContent.setContent(content);
        mainContentRepository.save(mainContent);
        List<Notification> notificationList = new ArrayList<>();
        mainContent = mainContentRepository.findFirstByOrderByIdDesc();
        for(int i = 0; i < accountList.size(); i++){
            Notification notification = new Notification();
            notification.setSent(LocalDateTime.now());
            notification.setAccountToNotification(accountList.get(i));
            notification.setMainContentToNotification(mainContent);
            notificationList.add(notification);
            notificationRepository.save(notification);
            accountList.get(i).getNotificationsFromAccount().add(notification);
            accountList.get(i).setNotificationsFromAccount(accountList.get(i).getNotificationsFromAccount());
        }
        mainContent.setNotificationsFromMainContent(notificationList);
        mainContentRepository.save(mainContent);

        accountRepository.saveAll(accountList);
        return findAllMess(count, size);
    }


    private String sha256(String password) {
        String sha256hex = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        return sha256hex;
    }
}
