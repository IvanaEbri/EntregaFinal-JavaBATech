package com.techlab.NookBooks.book;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    //logica manejo categoria

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    public Category createCategory(Category category) {
        if (category.getCategoryName() == null) {
            System.out.println("Ingrese un nombre valido para crear la categoria.");
            return null;
        }
        System.out.println("Creando nueva categoria");

        return this.categoryRepository.save(category);
    }

    public List<Category> showCategorys(String category) {
        if (!category.isEmpty()) {
            return this.categoryRepository.findByCategoryNameContaining(category);
        }
        return this.categoryRepository.findAll();
    }

    public Category searchCategory (Long id){
        return this.categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Category editCategoryName(Long id, Category dataCategory) {
        // TODO: https://www.baeldung.com/java-optional-return
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro la categoria"));

        // VALIDACIONES
        if (dataCategory.getCategoryName() == null) {
            System.out.println("No se puede editar la Categoria. Ingrese un nombre valido.");
            return null;
        }

        category.setCategoryName(dataCategory.getCategoryName());
        this.categoryRepository.save(category);

        return category;
    }

    public Category deleteCategory(Long id) {
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            System.out.println("No se puede borrar la categoria. No se encontró la misma");
            return null;
        }

        Category category = categoryOptional.get();

        this.categoryRepository.delete(category);
        // se puede usar el siguiente codigo, pero hay que manejar una excepcion(OptimisticLockingFailureException)
        //this.repository.deleteById(category);

        System.out.println("Se borro correctamente el categoria: " + category.getCategoryName());
        return category;
    }
}
