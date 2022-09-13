package knight.springframework.cookbook.controllers;

import knight.springframework.cookbook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }

    @RequestMapping({"","/","/index","/index.html"})
    public String index(Model model) {
        log.debug("index controller invoked");
        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";

    }

}
