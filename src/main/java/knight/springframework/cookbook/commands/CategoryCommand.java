package knight.springframework.cookbook.commands;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    ///Command objs for my data model

    private Long id;
    private String description;


}
