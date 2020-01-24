package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/show/{id}")
    public String getRecipeById(@PathVariable String id, Model model) {
        Long longID = Long.valueOf(id);
        model.addAttribute("recipe", this.recipeService.findById(longID));

        return "recipe/show";
    }
}