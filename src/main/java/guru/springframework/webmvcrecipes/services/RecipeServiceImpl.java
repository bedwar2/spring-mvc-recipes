package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Recipe findById(Long id) {
        //return this.recipeRepository.findById(id).orElse(null);

        Optional<Recipe> recipeOptional = this.recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            throw new RuntimeException("Recipe Not Found!!");
        }

        return recipeOptional.get();
    }
}
