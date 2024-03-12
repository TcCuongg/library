package com.example.library.controller;

import com.example.library.entity.Notification;
import com.example.library.more.NotificationSave;
import com.example.library.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/addNewNotification")
    public Notification addNewNotification(@RequestBody NotificationSave notificationSave){
        return notificationService.addNewNotification(notificationSave.getMainContentId(), notificationSave.getAccountId());
    }
}
