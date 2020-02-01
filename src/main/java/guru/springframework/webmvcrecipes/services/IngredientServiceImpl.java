package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.IngredientCommand;
import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.converters.IngredientCommandToIngredient;
import guru.springframework.webmvcrecipes.converters.IngredientToIngredientCommand;
import guru.springframework.webmvcrecipes.domain.Ingredient;
import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import guru.springframework.webmvcrecipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeService recipeService;
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    RecipeRepository recipeRepository;

    public IngredientServiceImpl(RecipeService recipeService,
                                 UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand)
    {
        this.recipeService = recipeService;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long ingredId) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingred = recipeCommand.getIngredients().stream().filter(o -> o.getId() == Long.valueOf(ingredId)).findFirst().orElse(null);
        return ingred;
    }

    @Override
    @Transactional
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        Recipe recipe = recipeService.findById(recipeId);
        Ingredient ingred = recipe.getIngredientSet().stream().filter(o -> o.getId() == ingredientId).findFirst().orElse(null);
        ingred.setRecipe(null);
        if (ingred != null) {
            recipe.getIngredientSet().remove(ingred);
        }

        //recipe.setIngredientSet(recipe.getIngredientSet().stream().filter(o -> o.getId() != ingredientId).collect(Collectors.toSet()));
        recipeRepository.save(recipe);
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Recipe recipe = recipeService.findById(command.getRecipeId());

        if(recipe==null){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredientSet()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());

                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredientSet().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .findFirst()
                    .get());
        }

    }
}
