package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.converters.RecipeCommandToRecipe;
import guru.springframework.webmvcrecipes.converters.RecipeToRecipeCommand;
import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceTest {

    public static final String NEW_DESCRIPTION = "New description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;


    @Test
    void getAllRecipes() {

        /*
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe());
        recipes.add(new Recipe());
        Iterable<Recipe> recipesData = new Iterable<Recipe>() {
            @Override
            public Iterator<Recipe> iterator() {
                return recipes.iterator();
            }
        };

        when(recipeRepository.findAll()).thenReturn(recipesData);
        Iterable<Recipe> recipes1 = recipeService.getAllRecipes();
        Iterable<Recipe> recipes2 = recipeService.getAllRecipes();
        */
        assertEquals(2, StreamSupport.stream(recipeService.getAllRecipes().spliterator(), false).count());

        //verify(recipeRepository,times(2)).findAll();
    }

    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();

        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredientSet().size(), savedRecipeCommand.getIngredients().size());

    }


}