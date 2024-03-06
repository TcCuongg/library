package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartSave {
    private Long bookStorageId;
    private Long accountId;
    public CartSave(Long bookStorageId, Long accountId){
        this.bookStorageId = bookStorageId;
        this.accountId = accountId;
    }
}
