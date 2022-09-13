package knight.springframework.cookbook.controllers;

import knight.springframework.cookbook.commands.RecipeCommand;
import knight.springframework.cookbook.exceptions.NotFoundException;
import knight.springframework.cookbook.model.Recipe;
import knight.springframework.cookbook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.Validate;

@Slf4j
@Controller
public class RecipeController {

    private static final String RECIPE_RECIPE_FORM = "recipe/recipeform";
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe/{id}/show")//pick up id val out of url
    public String showById(@PathVariable String id, Model model) {

        model.addAttribute("recipe",recipeService.findById(new Long(id)));
        return "recipe/show";

    }



    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe",new RecipeCommand());
        return RECIPE_RECIPE_FORM;
    }


    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));



        model.addAttribute("recipe",recipeCommand);
        return RECIPE_RECIPE_FORM;
    }





    @PostMapping( "recipe")//existing recipe has an id new one does not and mod attr. binds command to model
    public String saveRecipe(@Validated @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_RECIPE_FORM;

        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }


    @GetMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");//view==webpage
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }



}
