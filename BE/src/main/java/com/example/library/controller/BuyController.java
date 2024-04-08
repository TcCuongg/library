package com.example.library.controller;

import com.example.library.entity.Buy;
import com.example.library.more.BookPay;
import com.example.library.more.BuySave;
import com.example.library.more.UpdateBuy;
import com.example.library.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buy")
public class BuyController {
    @Autowired
    public BuyService buyService;

    @PostMapping("/addNewBuy")
    public List<BuySave> addNewBuy(@RequestBody BuySave buySave){
        return buyService.addNewBuy(buySave.getBookStorageId(), buySave.getAccountId(), buySave.getCost(), buySave.getStatus(), buySave.getQuantity());
    }
    @PostMapping("/updateBuy")
    public BookPay updateBuy(@RequestBody UpdateBuy updateBuy){
        return buyService.updateBuy(updateBuy.getAccountId(), updateBuy.getBuyId(), updateBuy.getStatus());
    }
    @PostMapping("/updateBuyOk/{count}/{size}")
    public List<BookPay> updateBuyOk(@RequestBody UpdateBuy updateBuy, @PathVariable int count, @PathVariable int size){
        return buyService.updateBuyOk(updateBuy.getAccountId(), updateBuy.getBuyId(), updateBuy.getStatus(), count, size);
    }
}
