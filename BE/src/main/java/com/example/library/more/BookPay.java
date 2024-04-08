package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPay {
    private Long buyId;
    private Long bookStorageId;
    private String accountName;
    private Long accountPhone;
    private String accountEmail;
    private String accountAddress;
    private String bookTitle;
    private int buyQuantity;
    private Long buyCost;
    private int bookStrongQuantity;
    private String employee;
    private String buyStatus;

    public BookPay(){}
    public BookPay(Long buyId, Long bookStorageId, String accountName, Long accountPhone, String accountEmail, String accountAddress, String bookTitle, int buyQuantity
            , Long buyCost, int bookStrongQuantity, String buyStatus){
        this.buyId = buyId;
        this.bookStorageId = bookStorageId;
        this.accountName = accountName;
        this.accountPhone = accountPhone;
        this.accountEmail = accountEmail;
        this.accountAddress = accountAddress;
        this.bookTitle = bookTitle;
        this.buyQuantity = buyQuantity;
        this.buyCost = buyCost;
        this.bookStrongQuantity = bookStrongQuantity;
        this.buyStatus = buyStatus;
    }
    public BookPay(Long buyId, Long bookStorageId, String accountName, Long accountPhone, String accountEmail, String accountAddress, String bookTitle, int buyQuantity
            , Long buyCost, int bookStrongQuantity, String buyStatus, String employee){
        this.buyId = buyId;
        this.bookStorageId = bookStorageId;
        this.accountName = accountName;
        this.accountPhone = accountPhone;
        this.accountEmail = accountEmail;
        this.accountAddress = accountAddress;
        this.bookTitle = bookTitle;
        this.buyQuantity = buyQuantity;
        this.buyCost = buyCost;
        this.bookStrongQuantity = bookStrongQuantity;
        this.buyStatus = buyStatus;
        this.employee = employee;
    }
}
