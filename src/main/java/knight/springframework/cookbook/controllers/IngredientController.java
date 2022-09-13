package knight.springframework.cookbook.controllers;

import knight.springframework.cookbook.commands.IngredientCommand;
import knight.springframework.cookbook.commands.RecipeCommand;
import knight.springframework.cookbook.commands.UnitOfMeasureCommand;
import knight.springframework.cookbook.services.IngredientService;
import knight.springframework.cookbook.services.RecipeService;
import knight.springframework.cookbook.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService){
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand =new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient",ingredientCommand);
        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";




    }




    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(Model model, @PathVariable String recipeId){
       log.debug("Getting ingredient for recipe number: "+recipeId);
        model.addAttribute("recipe",
                recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/ingredient/list";

    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
    public String showRecipeIngredient(@PathVariable String recipeId,
                                       @PathVariable String id,Model model){
         model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(id)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String id,Model model) {
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(id)));
        model.addAttribute("uomList",unitOfMeasureService.listAllUoms());
        return "recipe/ingredient/ingredientform";


    }

    @PostMapping
    @RequestMapping("recipe/{recipeId}/ingredient")
    public String save(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("ingredient id "+savedCommand.getId());
        log.debug("recipe id "+savedCommand.getRecipeId());

        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";




    }


    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {

        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(id));
        return "redirect:/recipe/"+recipeId+"/ingredients";

    }




}
