package com.example.library.more;

import com.example.library.entity.MainContent;
import com.example.library.entity.Notification;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentSent {
    private MainContent mainContent;
    private Notification notification;
    public ContentSent(MainContent mainContent, Notification notification){
        this.mainContent = mainContent;
        this.notification = notification;
    }
}
