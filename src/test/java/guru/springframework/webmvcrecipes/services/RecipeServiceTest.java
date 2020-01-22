package guru.springframework.webmvcrecipes.services;

import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getAllRecipes() {

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

        assertEquals(2, StreamSupport.stream(recipes.spliterator(), false).count());

        verify(recipeRepository,times(2)).findAll();
    }
}