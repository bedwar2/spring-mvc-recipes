package guru.springframework.webmvcrecipes.bootstrap;

import guru.springframework.webmvcrecipes.domain.*;
import guru.springframework.webmvcrecipes.repositories.CategoryRepository;
import guru.springframework.webmvcrecipes.repositories.RecipeRepository;
import guru.springframework.webmvcrecipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class DataLoader {

    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    public void loadData() {

        log.debug("Loading the data now!");
        Recipe guac = new Recipe();
        guac.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.");
        //guac.setNotes(guacNotes);
        Set<Category> categorySet = new HashSet<>();
        guac.getCategories().add(categoryRepository.findByCategoryName("Mexican").get());
        guac.getCategories().add(categoryRepository.findByCategoryName("American").get());
        //categorySet.add(categoryRepository.findByCategoryName("Mexican").get());
        //categorySet.add(categoryRepository.findByCategoryName("American").get());
        //guac.setCategories(categorySet);
        guac.setPrepTime(10);

        Ingredient ing1 = new Ingredient();
        ing1.setAmount(BigDecimal.valueOf(2));
        ing1.setDescription("ripe avocados");
        ing1.setRecipe(guac);
        guac.getIngredientSet().add(ing1);
        ing1 = new Ingredient();
        ing1.setAmount(BigDecimal.valueOf(0.25));
        ing1.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        ing1.setDescription("salt, more to taste");
        ing1.setRecipe(guac);
        guac.getIngredientSet().add(ing1);
        guac.setCookTime(0);
        guac.setServings(5);
        guac.setDifficulty(Difficulty.EASY);
        guac.setDescription("Perfect Guacamole");
        guac = recipeRepository.save(guac);
        System.out.println("Recipe added");
        System.out.println(guac);


    }
}
