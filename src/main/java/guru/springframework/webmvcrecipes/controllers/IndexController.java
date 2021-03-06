package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.domain.Category;
import guru.springframework.webmvcrecipes.domain.UnitOfMeasure;
import guru.springframework.webmvcrecipes.repositories.CategoryRepository;
import guru.springframework.webmvcrecipes.repositories.UnitOfMeasureRepository;
import guru.springframework.webmvcrecipes.services.RecipeService;
import guru.springframework.webmvcrecipes.services.RecipeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@Slf4j
public class IndexController {
    //private CategoryRepository categoryRepository;
    //private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeService recipeService;

    /*
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeServiceImpl recipeService) {

        log.debug("In the Index Controller");
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }
    */
    public IndexController(RecipeService recipeService) {

        log.debug("In the Index Controller");
        this.recipeService = recipeService;
    }

    @RequestMapping({"/", "", "/index", "index.html"})
    public String getIndexPage(Model model){

        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "index";
    }

    @RequestMapping({"/recipes", "recipes.html"})
    public String recipeList(Model model) {

        log.info("in recipeList method");
        model.addAttribute("recipes", recipeService.getAllRecipes());

        return "recipes";
    }

    @RequestMapping()
    public String getRecipeById(Model model) {
        return "recipe";
    }

}
