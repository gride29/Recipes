package recipes.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Map<String, ?> add(Recipe recipe) {
        recipeRepository.save(recipe);
        return Map.of(
                "id", recipe.getId()
        );
    }

    public Recipe get(long id) {
        return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }

    public ResponseEntity<Void> delete(long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException();
        }
        recipeRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> update(long id, Recipe updatedRecipe) {
        Recipe recipe = get(id);
        recipe.setName(updatedRecipe.getName());
        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setCategory(updatedRecipe.getCategory());
        recipe.setIngredients(updatedRecipe.getIngredients());
        recipe.setDirections(updatedRecipe.getDirections());
        recipe.setDate(LocalDateTime.now().toString());
        recipeRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public Object searchRecipe(String category, String name) {
        if (category != null && name != null || category == null && name == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            if (category != null) {
                return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
            } else {
                return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
            }
        }
    }
}
