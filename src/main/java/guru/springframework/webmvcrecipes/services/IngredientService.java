package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.IngredientCommand;

public interface IngredientService {
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long IngredientId);
    public IngredientCommand saveIngredientCommand(IngredientCommand command);
    public void deleteIngredient(Long recipeId, Long ingredientId);
}
