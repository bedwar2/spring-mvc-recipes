package guru.springframework.webmvcrecipes.repositories;

import guru.springframework.webmvcrecipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
