package com.example.library.service;

import com.example.library.entity.Notification;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.MainContentRepository;
import com.example.library.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private MainContentRepository mainContentRepository;
    @Autowired
    private AccountRepository accountRepository;

    public Notification addNewNotification(Long mainContentId, Long accountId){
        Timestamp timestamp = Timestamp.from(Instant.now());
        Notification notification = new Notification();
        notification.setAccountToNotification(accountRepository.findFirstByCardNumber(accountId));
        notification.setMainContentToNotification(mainContentRepository.findFirstById(mainContentId));
        notification.setSent(timestamp);
        return notificationRepository.save(notification);
    }
}
