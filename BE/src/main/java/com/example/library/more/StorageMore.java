package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StorageMore {
    private String bookStorageId;
    private String image;
    private String quantity;
    private Long accountId;
    public StorageMore(String bookStorageId, String image, String quantity, Long accountId){
        this.bookStorageId = bookStorageId;
        this.image = image;
        this.quantity = quantity;
        this.accountId = accountId;
    }
}
