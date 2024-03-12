package com.example.library.service;

import com.example.library.entity.Account;
import com.example.library.entity.BookStorage;
import com.example.library.entity.Cart;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.BookStorageRepository;
import com.example.library.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Account account = accountRepository.findFirstByCardNumber(accountId);
        BookStorage bookStorage = bookStorageRepository.findFirstById(bookStorageId);
        cart.setAccountToCart(account);
        cart.setBookStorageToCart(bookStorage);

        account.getCartsFromAccount().add(cart);
        account.setCartsFromAccount(account.getCartsFromAccount());
        accountRepository.save(account);

        bookStorage.getCartsFromBookStorage().add(cart);
        bookStorage.setCartsFromBookStorage(bookStorage.getCartsFromBookStorage());
        bookStorageRepository.save(bookStorage);

        return cartRepository.save(cart);
    }
}
