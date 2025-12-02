package com.techlab.book;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService service;

    public CategoryController(CategoryService service) {

        this.service = service;
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {

        return this.service.createCategory(category);
    }

    // GET /products?category="categoria"
    @GetMapping
    public List<Category> showCategorys(
            @RequestParam(required = false, defaultValue = "") String category){
        return this.service.showCategorys(category);
    }

    //@PatchMapping
    @PutMapping("/{id}")
    public Category editCategory(@PathVariable Long id, @RequestBody Category category) {
        return this.service.editCategoryName(id, category);
    }

    /* no se deberia poder borrar completamente una categoria
    @DeleteMapping("/{id}")
    public Category borrarcategory(@PathVariable(name = "id") Long categoryId) {
        return this.service.deleteCategory(categoryId);
    }
     */

}
