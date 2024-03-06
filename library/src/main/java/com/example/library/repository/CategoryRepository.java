package com.example.library.repository;

import com.example.library.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findFirstByName(String name);

    @Query("select category from Category category")
    public List<Category> getCategories(Pageable pageable);

    @Query("select category from Category category where category.name = :name")
    public List<Category> getCategoriesByTitle(@Param("name") String name, Pageable pageable);

    public Category findFirstById(Long id);
}
