package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.CategoryCommand;
import guru.springframework.webmvcrecipes.converters.CategoryToCategoryCommand;
import guru.springframework.webmvcrecipes.domain.Category;
import guru.springframework.webmvcrecipes.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand converter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand converter) {
        this.categoryRepository = categoryRepository;
        this.converter = converter;
    }

    @Override
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Iterable<CategoryCommand> getAllCategoryCommands() {
        List<CategoryCommand> catCmds = new ArrayList<>();
        getAllCategories().forEach(cat -> {
            catCmds.add(converter.convert(cat));
        });

        return catCmds;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public CategoryCommand findCommandById(Long id) {
        return converter.convert(this.findById(id));
    }
}
