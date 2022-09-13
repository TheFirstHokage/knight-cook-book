package knight.springframework.cookbook.services;

import knight.springframework.cookbook.commands.RecipeCommand;
import knight.springframework.cookbook.converters.RecipeCommandToRecipe;
import knight.springframework.cookbook.converters.RecipeToRecipeCommand;
import knight.springframework.cookbook.exceptions.NotFoundException;
import knight.springframework.cookbook.model.Recipe;
import knight.springframework.cookbook.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    public RecipeRepository getRecipeRepository() {
        return recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
      log.debug("Im in the service");

        Set<Recipe> setOfRecipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipe -> {
            setOfRecipes.add(recipe);
        });

        return setOfRecipes;
    }

    public Recipe findById(Long id){
        Optional<Recipe> recipe =recipeRepository.findById(id);
        if (!recipe.isPresent()){
          //  throw new RuntimeException("Recipe not found");
            throw new NotFoundException("Recipe not found for id: "+ id.toString());
        }
        return recipe.get();
    }


    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("saved id :"+savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        return recipeToRecipeCommand.convert(findById(l));
    }


    @Override
    public void deleteById(Long idToDelete){
        recipeRepository.deleteById(idToDelete);
    }



}
