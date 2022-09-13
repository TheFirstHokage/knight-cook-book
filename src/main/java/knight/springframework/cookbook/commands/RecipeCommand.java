package knight.springframework.cookbook.commands;
import knight.springframework.cookbook.model.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {


    private Long id;

    @NotEmpty
    @NotNull
    @Size(min = 3,max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL
    @NotNull
    @NotEmpty
    private String url;

    @NotEmpty
    @NotNull
    private String directions;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Byte[] image;
    private Set<CategoryCommand> categories = new HashSet<>();


}


