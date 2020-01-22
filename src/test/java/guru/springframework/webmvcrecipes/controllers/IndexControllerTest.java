package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.domain.Recipe;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import guru.springframework.webmvcrecipes.services.RecipeService;
import guru.springframework.webmvcrecipes.services.RecipeServiceImpl;
import org.h2.index.Index;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
class IndexControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    Model model;



    IndexController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        //recipeService = new RecipeServiceImpl(recipeRepository);

        controller = new IndexController(recipeService);
    }

    @Test
    public void TestMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void recipeList() {

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe());
        recipeList.add(new Recipe());
        Iterable<Recipe> recipeIterable = new Iterable<Recipe>() {
            @Override
            public Iterator<Recipe> iterator() {
                return recipeList.iterator();
            }
        };

        when(recipeService.getAllRecipes()).thenReturn(recipeIterable);

        ArgumentCaptor<Iterable<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Iterable.class);

        String viewName = controller.recipeList(model);

        assertEquals("recipes", viewName);
        //assertEquals(2, StreamSupport.stream(((Iterable<Recipe>) model.getAttribute("recipes")).spliterator(), false).count());

        verify(recipeService, times(1)).getAllRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Iterable<Recipe> recipeIterable1 = argumentCaptor.getValue();

        assertEquals(2, StreamSupport.stream(recipeIterable1.spliterator(), false).count());


    }
}