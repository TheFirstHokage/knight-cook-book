package knight.springframework.cookbook.services;

import knight.springframework.cookbook.commands.IngredientCommand;
import knight.springframework.cookbook.converters.IngredientCommandToIngredient;
import knight.springframework.cookbook.converters.IngredientToIngredientCommand;
import knight.springframework.cookbook.model.Ingredient;
import knight.springframework.cookbook.model.Recipe;
import knight.springframework.cookbook.repositories.RecipeRepository;
import knight.springframework.cookbook.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private  final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }





    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling -- 404 not found
            log.error("recipe not found: "+recipeId);
        }

        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> ingredientCommandOptional =
                recipe.getIngredients().stream().filter(ingredient -> ingredient.getId()
                        .equals(ingredientId)).map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("ingredient not found");

        }
        return ingredientCommandOptional.get();

    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command){

        //todo toss err for not found-notes service layer for ingredient form that takes in the ingredient command
        //object and converts it to ingredient. applies changes from form and adds
        //ingredient to recipe model


        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("recipe not found for id "+command.getId());

            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();

            //getting ingredient --find first returns optional
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(command.getId())).findFirst();


            if (ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(command.getAmount());
                ingredient.setDescription(command.getDescription());
                ingredient.setUom(unitOfMeasureRepository.findById(command.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("uom not found")));//todo address this

            }else {
                //if not an existing ingredient add it to recipe
                //recipe.addIngredient(ingredientCommandToIngredient.convert(command));
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);

            }
            //persists w hibernate
            Recipe savedRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients()
                    .stream().filter(ingredient -> ingredient.getId().equals(command.getId())).
                    findFirst();

            if (!savedIngredientOptional.isPresent()) {
                 savedIngredientOptional = savedRecipe.getIngredients()
                        .stream().filter(ingredient ->ingredient.getDescription().equals(command.getDescription()))
                        .filter(ingredient ->ingredient.getAmount().equals(command.getAmount()) )
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();

            }
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());





//            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients()
//                    .stream().filter(ingredients -> ingredients.getId().equals(command.getId()))
//                    .findFirst().get());

        }
    }


    @Override
    public void deleteById(Long recipeId, Long id) {


        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream().filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();

                if (ingredientOptional.isPresent()) {
                    Ingredient ingredientToDelete =  ingredientOptional.get();
                    ingredientToDelete.setRecipe(null);
                    recipe.getIngredients().remove(ingredientOptional.get());
                    recipeRepository.save(recipe);
                }



        }else {
            log.error("No recipe found "+recipeId);
        }

    }
}
