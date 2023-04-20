package com.cra.portfolio.controller;

import com.cra.portfolio.model.Category;
import com.cra.portfolio.model.Question;
import com.cra.portfolio.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }
    @GetMapping("/{CategoryId}")
    public List<Question> GetAllCategoryQuestions(@PathVariable Integer CategoryId) {
        return categoryService.getAllCategoryQuestions(CategoryId);

    }
}
