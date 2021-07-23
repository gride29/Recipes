package recipes.user;

import lombok.Data;
import lombok.ToString;
import recipes.recipe.Recipe;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @NotBlank
    @Pattern(regexp = ".*@.*\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @ToString.Exclude
    private List<Recipe> ownedRecipes = new ArrayList<>();
}
