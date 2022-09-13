package knight.springframework.cookbook.services;

import knight.springframework.cookbook.commands.IngredientCommand;


public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteById(Long id1,Long id2);

}
