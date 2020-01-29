package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.converters.RecipeCommandToRecipe;
import guru.springframework.webmvcrecipes.converters.RecipeToRecipeCommand;
import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
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

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();

        recipeRepository.findAll().forEach(o -> recipes.add(o));
        return recipes;
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = this.recipeCommandToRecipe.convert(command);

        RecipeCommand recipeCommand =  this.recipeToRecipeCommand.convert(recipeRepository.save(detachedRecipe));
        log.debug("Saved Recipe ID: " + recipeCommand.getId());
        return recipeCommand;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(this.findById(id));
    }
}
