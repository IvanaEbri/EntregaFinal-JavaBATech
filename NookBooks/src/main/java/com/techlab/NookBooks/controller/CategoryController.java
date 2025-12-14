package com.techlab.NookBooks.controller;

import com.techlab.NookBooks.service.CategoryService;
import com.techlab.NookBooks.model.entity.Category;
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
    public Category createCategory(@RequestBody Category category) {

        return this.categoryService.createCategory(category);
    }

    // GET /category?category="categoria"
    @GetMapping
    public List<Category> showCategorys(
            @RequestParam(required = false, defaultValue = "") String category){
        return this.categoryService.showCategorys(category);
    }

    //@PatchMapping
    @PutMapping("/{id}")
    public Category editCategory(@PathVariable Long id, @RequestBody Category category) {
        return this.categoryService.editCategoryName(id, category);
    }

    /* no se deberia poder borrar completamente una categoria
    @DeleteMapping("/{id}")
    public Category borrarcategory(@PathVariable(name = "id") Long categoryId) {
        return this.service.deleteCategory(categoryId);
    }
     */

}
