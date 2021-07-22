package recipes.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Recipe {

    @Getter(onMethod = @__( @JsonIgnore ))
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    @ElementCollection()
    private List<String> ingredients;

    @NotEmpty
    @ElementCollection()
    private List<String> directions;

    @NotBlank
    private String category;

    @NotBlank
    private String date = LocalDateTime.now().toString();
}
