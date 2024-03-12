package com.example.library.more;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Sale {
    private Long id;
    private int sale;
    public Sale(Long id, int sale){
        this.id = id;
        this.sale = sale;
    }
}
