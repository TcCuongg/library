package com.example.library.controller;

import com.example.library.entity.Buy;
import com.example.library.more.BuySave;
import com.example.library.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    public BuyService buyService;

    @PostMapping("/addNewBuy")
    public Buy addNewBuy(@RequestBody BuySave buySave){
        return buyService.addNewBuy(buySave.getBookStorageId(), buySave.getAccountId(), buySave.getCost(), buySave.getStatus());
    }
}
