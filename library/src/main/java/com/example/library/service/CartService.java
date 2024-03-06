package com.example.library.service;

import com.example.library.entity.Account;
import com.example.library.entity.BookStorage;
import com.example.library.entity.Cart;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.BookStorageRepository;
import com.example.library.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BookStorageRepository bookStorageRepository;

    public Cart addNewCart(Long bookStorageId, Long accountId){
        Cart cart = new Cart();
        cart.setAccountToCart(accountRepository.findFirstByCardNumber(accountId));
        cart.setBookStorageToCart(bookStorageRepository.findFirstById(bookStorageId));
        return cartRepository.save(cart);
    }
}
