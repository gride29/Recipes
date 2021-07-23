package recipes.recipe;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/new")
    public Map<String, ?> addRecipe(@RequestBody @Valid Recipe recipe) {
        return recipeService.add(recipe);
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        return recipeService.get(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id) {
        return recipeService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable long id, @RequestBody @Valid Recipe recipe) {
        return recipeService.update(id, recipe);
    }

    @GetMapping("/search")
    public Object searchRecipe(@RequestParam(required = false) String category,
                               @RequestParam(required = false) String name) {
        return recipeService.searchRecipe(category, name);
    }
}
