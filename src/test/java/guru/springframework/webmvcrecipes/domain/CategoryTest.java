package guru.springframework.webmvcrecipes.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    public static Category category;


    @BeforeAll
    public static void setupTest() {
        category = new Category();
    }

    @Test
    void getId() {
        category.setId(4L);

        assertEquals(4L, category.getId());
    }

    @Test
    void getCategoryName() {

        category.setCategoryName("Test");

        assertEquals("Test", category.getCategoryName());
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe rep1 = new Recipe();
        rep1.setDescription("This is a test");
        recipeSet.add(rep1);
        category.setRecipes(recipeSet);

        assertEquals(1, category.getRecipes().size());
    }
}