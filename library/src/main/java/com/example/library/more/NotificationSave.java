package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class NotificationSave {
    private Long mainContentId;
    private Long accountId;
    public NotificationSave(Long mainContentId, Long accountId){
        this.mainContentId = mainContentId;
        this.accountId = accountId;
    }
}
