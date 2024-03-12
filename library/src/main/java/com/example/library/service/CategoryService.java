package com.example.library.service;

import com.example.library.entity.Category;
import com.example.library.more.BookMore;
import com.example.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    public List<Category> getCategory(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return categoryRepository.getCategories(pageable);
    }
    public List<Category> getCategoryByTitle(String title, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        return categoryRepository.getCategoriesByTitle(title, pageable);
    }
    public Category findFirstById(Long id, int sale){
        Category category = categoryRepository.findFirstById(id);
        category.setSale(sale);
        return categoryRepository.save(category);
    }
    public Category addNewCategory(Category category){
        return categoryRepository.save(category);
    }
    public List<String> findAllCategoryName(){
        return categoryRepository.findAllCategoryName();
    }
}
