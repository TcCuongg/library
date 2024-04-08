package com.example.library.service;

import com.example.library.entity.Account;
import com.example.library.entity.BookStorage;
import com.example.library.entity.Cart;
import com.example.library.more.CartSave;
import com.example.library.repository.AccountRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookStorageRepository;
import com.example.library.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BookStorageRepository bookStorageRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RedisTemplate redisCartTemplate;

    public List<CartSave> addNewCart(Long bookStorageId, Long accountId){
        int getCountAllBookByAccountCart = bookRepository.getCountAllBookByAccountCart(accountId);
        List<CartSave> cartList = new ArrayList<>();
        Cart cart = new Cart();
        Account account = accountRepository.findFirstByCardNumber(accountId);
        BookStorage bookStorage = bookStorageRepository.findFirstById(bookStorageId);
        cart.setAccountToCart(account);
        cart.setBookStorageToCart(bookStorage);

        redisCartTemplate.delete("findAllBookByAccountCart:" + accountId +"(" + getCountAllBookByAccountCart/8 + ", " + 8 + ")");

        cartRepository.save(cart);
        cartList.add(new CartSave(bookStorageId, accountId));
        return cartList;
    }
}
