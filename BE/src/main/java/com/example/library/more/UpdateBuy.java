package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBuy {
    private Long accountId;
    private Long buyId;
    private String status;
    public UpdateBuy(){}
    public UpdateBuy(Long accountId, Long buyId, String status){
        this.accountId = accountId;
        this.buyId = buyId;
        this.status = status;
    }
}
