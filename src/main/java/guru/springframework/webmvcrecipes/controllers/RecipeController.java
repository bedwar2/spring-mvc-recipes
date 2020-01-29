package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.converters.RecipeToRecipeCommand;
import guru.springframework.webmvcrecipes.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;


    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model) {
        Long longID = Long.valueOf(id);
        model.addAttribute("recipe", this.recipeService.findById(longID));

        return "recipe/show";
    }

    @GetMapping(value = "/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping(value = "/{id}/update")
    public String updateRecipe(@PathVariable String id,  Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/recipeform";
    }

    @PostMapping(name = "/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
