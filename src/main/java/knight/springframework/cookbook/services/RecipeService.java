package knight.springframework.cookbook.services;

import knight.springframework.cookbook.commands.RecipeCommand;
import knight.springframework.cookbook.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findById(Long l);
    RecipeCommand saveRecipeCommand(RecipeCommand command);
    RecipeCommand findCommandById(Long id);
    void deleteById(Long id);

}
