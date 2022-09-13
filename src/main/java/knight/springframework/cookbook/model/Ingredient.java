package knight.springframework.cookbook.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
//@Data//getter,setter,equals,hashcode,constructors--exclude recipe bc of jpa relationship
@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;
    @OneToOne(fetch = FetchType.EAGER)
     private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;

    public Ingredient() {

    }
    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;

    }


    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom,Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public UnitOfMeasure getUom() {
//        return uom;
//    }
//
//    public void setUom(UnitOfMeasure uom) {
//        this.uom = uom;
//    }
//
//    public Recipe getRecipe() {
//        return recipe;
//    }
//
//    public void setRecipe(Recipe recipe) {
//        this.recipe = recipe;
//    }
}
