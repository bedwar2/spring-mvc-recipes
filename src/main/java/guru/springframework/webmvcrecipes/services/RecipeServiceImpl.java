package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
