package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.CategoryCommand;
import guru.springframework.webmvcrecipes.domain.Category;


public interface CategoryService {
    public Iterable<Category> getAllCategories();

    public Iterable<CategoryCommand> getAllCategoryCommands();

    public Category findById(Long id);

    public CategoryCommand findCommandById(Long id);
}
