package com.example.library.service;

import com.example.library.entity.Category;
import com.example.library.more.BookManage;
import com.example.library.more.BookMore;
import com.example.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RedisTemplate redisStringTemplate;
    @Autowired
    private RedisTemplate redisCategoryTemplate;

    public List<Category> getAllCategory(){
        List<Category> categoryList = new ArrayList<>();

        String redisKey = "getAllCategory";

        boolean hasKey = redisCategoryTemplate.hasKey(redisKey);

        if(hasKey){
            categoryList = redisCategoryTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            categoryList = categoryRepository.findAll();
            if(!categoryList.isEmpty()){
                redisCategoryTemplate.opsForList().rightPushAll(redisKey, categoryList);
            }
        }
        return categoryList;
//        return categoryRepository.findAll();
    }
    public List<Category> getCategory(int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Category> categoryList = new ArrayList<>();

        String redisKey = "getCategory(" + count + ", " + size + ")";

        boolean hasKey = redisCategoryTemplate.hasKey(redisKey);

        if(hasKey){
            categoryList = redisCategoryTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            categoryList = categoryRepository.getCategories(pageable);
            Collections.reverse(categoryList);
            if(!categoryList.isEmpty()){
                redisCategoryTemplate.opsForList().rightPushAll(redisKey, categoryList);
            }
        }
        return categoryList;
//        return categoryRepository.getCategories(pageable);
    }
    public List<Category> getCategoryByTitle(String title, int count, int size){
        Pageable pageable = PageRequest.of(count, size);
        List<Category> categoryList = new ArrayList<>();

        String redisKey = "getCategoryByTitle:" + title + "(" + count + ", " + size + ")";

        boolean hasKey = redisCategoryTemplate.hasKey(redisKey);

        if(hasKey){
            categoryList = redisCategoryTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            categoryList = categoryRepository.getCategoriesByTitle(title, pageable);
            Collections.reverse(categoryList);
            if(!categoryList.isEmpty()){
                redisCategoryTemplate.opsForList().rightPushAll(redisKey, categoryList);
            }
        }
        return categoryList;
//        return categoryRepository.getCategoriesByTitle(title, pageable);
    }
    public List<String> findAllCategoryName(){
        List<String> stringList = new ArrayList<>();

        String redisKey = "findAllCategoryName";

        boolean hasKey = redisStringTemplate.hasKey(redisKey);

        if(hasKey){
            stringList = redisStringTemplate.opsForList().range(redisKey, 0, -1);
        }
        else {
            stringList = categoryRepository.findAllCategoryName();
            if(!stringList.isEmpty()){
                redisStringTemplate.opsForList().rightPushAll(redisKey, stringList);
            }
        }
        return stringList;
//        return categoryRepository.findAllCategoryName();
    }




    public List<Category> findFirstById(Long id, String sale){
        List<Category> categoryList = new ArrayList<>();
        if(!Objects.equals(sale, "")){
            Category category = categoryRepository.findFirstById(id);
            category.setSale(Integer.parseInt(sale));
            categoryRepository.save(category);
            categoryList.add(category);
            redisCategoryTemplate.delete("getAllCategory");
            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getCategory(*"));
            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getCategoryByTitle:" + category.getName() + "(*"));

            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getAllBook(*"));
            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getBookFollowDesc(*"));
            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getBookByTitle:*"));
            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getBookByCategory:" + category.getName() +"(*"));

            redisCategoryTemplate.delete(redisCategoryTemplate.keys("findAllBookByRequest:*"));

            return categoryList;
        }return categoryList;
    }
    public List<Category> addNewCategory(Category category){
        List<Category> categoryList = new ArrayList<>();
        if(!Objects.equals(category.getName(), "")){
            redisCategoryTemplate.delete("getAllCategory");
            redisCategoryTemplate.delete("findAllCategoryName");
            redisCategoryTemplate.delete(redisCategoryTemplate.keys("getCategory("+categoryRepository.getCountCategories()/4+", 4)"));
            categoryRepository.save(category);
            categoryList.add(categoryRepository.findFirstByOrderByIdDesc());
            return categoryList;
        }
        return categoryList;
    }

    public int getCountCategories(){
        return categoryRepository.getCountCategories();
    }
}
