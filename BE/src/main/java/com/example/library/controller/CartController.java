package com.example.library.controller;

import com.example.library.entity.Cart;
import com.example.library.more.CartSave;
import com.example.library.repository.*;
import com.example.library.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/addNewCart")
    public List<CartSave> addNewCart(@RequestBody CartSave cartSave){
        return cartService.addNewCart(cartSave.getBookStorageId(), cartSave.getAccountId());
    }
}
