package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Iterable<Recipe> getAllRecipes();

    Recipe findById(Long id);

    Set<Recipe> getRecipes();

    RecipeCommand saveRecipeCommand(RecipeCommand command);
}
