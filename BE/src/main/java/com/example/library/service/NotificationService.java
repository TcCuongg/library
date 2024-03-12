package com.example.library.service;

import com.example.library.entity.Account;
import com.example.library.entity.MainContent;
import com.example.library.entity.Notification;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.MainContentRepository;
import com.example.library.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MainContentRepository mainContentRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Notification addNewNotification(Long mainContentId, Long accountId){
        Notification notification = new Notification();
        Account account = accountRepository.findFirstByCardNumber(accountId);
        MainContent mainContent = mainContentRepository.findFirstById(mainContentId);
        notification.setAccountToNotification(account);
        notification.setMainContentToNotification(mainContent);
        notification.setSent(LocalDateTime.now());

        account.getNotificationsFromAccount().add(notification);
        account.setNotificationsFromAccount(account.getNotificationsFromAccount());
        accountRepository.save(account);

        mainContent.getNotificationsFromMainContent().add(notification);
        mainContent.setNotificationsFromMainContent(mainContent.getNotificationsFromMainContent());
        mainContentRepository.save(mainContent);

        return notificationRepository.save(notification);
    }
}
