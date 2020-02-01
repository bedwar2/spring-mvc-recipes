package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.commands.IngredientCommand;
import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.commands.UnitOfMeasureCommand;
import guru.springframework.webmvcrecipes.services.IngredientService;
import guru.springframework.webmvcrecipes.services.RecipeService;
import guru.springframework.webmvcrecipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

@Slf4j
@Controller
public class IngredientController {

    RecipeService recipeService;
    IngredientService ingredientService;
    UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping(value = "/recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredients/index";
    }

    @GetMapping(value = "/recipe/{recipeId}/ingredients/{ingredId}/show")
    public String getIngredient(@PathVariable String recipeId, @PathVariable String ingredId, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingredId)));
        return "recipe/ingredients/show";
    }

    @GetMapping(value = "/recipe/{recipeId}/ingredients/{ingredId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredId, Model model) {

        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(Long.valueOf(recipeId), Long.valueOf(ingredId)));
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitsOfMeasure());
        return "recipe/ingredients/ingredientform";
    }

    @PostMapping(value = "/recipe/{recipeId}/ingredient")
    public String saveIngredient(@ModelAttribute IngredientCommand ingredient) {
        log.debug(ingredient.toString());
        ingredientService.saveIngredientCommand(ingredient);

        return "redirect:/recipe/" + ingredient.getRecipeId() + "/ingredients";

    }

    @GetMapping(value = "/recipe/{recipeId}/ingredients/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setRecipeId(Long.valueOf(recipeId));
        ingredient.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("uomList", unitOfMeasureService.getAllUnitsOfMeasure());


        return "recipe/ingredients/ingredientform";

    }

    @GetMapping(value = "/recipe/{recipeId}/ingredients/{ingredId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredId) {
        log.debug(recipeId + " - " + ingredId);
        ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
