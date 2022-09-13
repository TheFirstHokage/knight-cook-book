package knight.springframework.cookbook.repositories;

import knight.springframework.cookbook.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Long> {


}
