package com.techlab.NookBooks.controller;

import com.techlab.NookBooks.service.CategoryService;
import com.techlab.NookBooks.model.entity.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.createCategory(category));
    }

    // GET /category?category="categoria"
    @GetMapping
    public ResponseEntity<List<Category>> showCategorys(
            @RequestParam(required = false, defaultValue = "") String category){
        return ResponseEntity.status(HttpStatus.OK).body(this.categoryService.showCategorys(category));
    }

    //@PatchMapping
    @PutMapping("/{id}")
    public ResponseEntity<Category> editCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(this.categoryService.editCategoryName(id, category));

    }

    /* no se deberia poder borrar completamente una categoria
    @DeleteMapping("/{id}")
    public Category borrarcategory(@PathVariable(name = "id") Long categoryId) {
        return this.service.deleteCategory(categoryId);
    }
     */

}
