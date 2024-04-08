package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class BuySave {
    private Long bookStorageId;
    private Long accountId;
    private String status;
    private Long cost;
    private int quantity;
    public BuySave(){}
    public BuySave(Long bookStorageId, Long accountId, String status, Long cost, int quantity){
        this.bookStorageId = bookStorageId;
        this.accountId = accountId;
        this.status = status;
        this.cost = cost;
        this.quantity = quantity;
    }
}
