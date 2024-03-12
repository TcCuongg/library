package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageSave {
    private String storageId;
    private String status;

    public StorageSave(String storageId, String status){
        this.storageId = storageId;
        this.status = status;
    }
}
