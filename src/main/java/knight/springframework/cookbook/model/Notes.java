package knight.springframework.cookbook.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
@Data
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne//dont cascade here bc we want recipe to own the note so if recipe is deleted so will the notes but not vice versa
    private Recipe recipe;
    @Lob
    private String recipeNotes;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Recipe getRecipe() {
//        return recipe;
//    }
//
//    public void setRecipe(Recipe recipe) {
//        this.recipe = recipe;
//    }
//
//    public String getRecipeNotes() {
//        return recipeNotes;
//    }
//
//    public void setRecipeNotes(String recipeNotes) {
//        this.recipeNotes = recipeNotes;
//    }
}
