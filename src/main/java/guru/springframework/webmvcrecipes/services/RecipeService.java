package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.domain.Recipe;

public interface RecipeService {
    Iterable<Recipe> getAllRecipes();
}
