package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sale {
    private Long id;
    private String sale;
    public Sale(Long id, String sale){
        this.id = id;
        this.sale = sale;
    }
}
