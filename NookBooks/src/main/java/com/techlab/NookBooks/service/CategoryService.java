package com.techlab.NookBooks.service;

import com.techlab.NookBooks.exception.CheckedDataException;
import com.techlab.NookBooks.exception.NotFoundException;
import com.techlab.NookBooks.exception.NullException;
import com.techlab.NookBooks.model.entity.Category;
import com.techlab.NookBooks.repository.CategoryRepository;
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

    public Category createCategory(Category category)throws NullException {
        if (category.getCategoryName() == null) {
            throw new NullException("Ingrese un nombre valido para crear la categoria.");
        }

        return this.categoryRepository.save(category);
    }

    public List<Category> showCategorys(String category) {
        if (!category.isEmpty()) {
            return this.categoryRepository.findByCategoryNameContaining(category);
        }
        return this.categoryRepository.findAll();
    }

    public Category searchCategory (Long id) throws NotFoundException {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Categoría no encontrada."));
    }

    public Category editCategoryName(Long id, Category dataCategory) throws  NotFoundException, CheckedDataException {
        // TODO: https://www.baeldung.com/java-optional-return
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontró la categoria"));

        // VALIDACIONES
        if (dataCategory.getCategoryName() == null) {
            throw new CheckedDataException("No se puede editar la Categoria. Ingrese un nombre valido.");
        }

        category.setCategoryName(dataCategory.getCategoryName());
        this.categoryRepository.save(category);

        return category;
    }

    public Category deleteCategory(Long id) {
        Optional<Category> categoryOptional = this.categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new NotFoundException("No se puede borrar la categoria. No se encontró la misma.");
        }

        Category category = categoryOptional.get();

        this.categoryRepository.delete(category);
        // se puede usar el siguiente codigo, pero hay que manejar una excepcion(OptimisticLockingFailureException)
        //this.repository.deleteById(category);
        return category;
    }
}
