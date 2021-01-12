package com.switchplaybackend.demo.api;

import com.switchplaybackend.demo.model.Category;
import com.switchplaybackend.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    @PostMapping("/add-category")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) throws URISyntaxException {
        Category result = categoryRepository.save(category);
        return ResponseEntity.created(new URI("/api/add-category" + result.getId())).body(result);
    }

<<<<<<< HEAD
=======


>>>>>>> soare
}
