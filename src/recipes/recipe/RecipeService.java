package recipes.recipe;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import recipes.config.IAuthenticationImpl;
import recipes.user.User;
import recipes.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IAuthenticationImpl authenticationImpl;
    private final UserRepository userRepository;

    public Map<String, ?> add(Recipe recipe) {
        recipe.setUser(getLoggedUser());
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
        Recipe recipe = get(id);
        if (recipe.getUser().equals(getLoggedUser())) {
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    private User getLoggedUser() {
        Object auth = authenticationImpl.getAuthentication();
        // If user is not authenticated respond with 403 status code
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        // Get data about user by calling getPrincipal()
        Object loggedUser = authenticationImpl.getAuthentication().getPrincipal();
        return userRepository.findById(((User) loggedUser).getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Void> update(long id, Recipe updatedRecipe) {
        Recipe recipe = get(id);
        if (recipe.getUser().getId() == getLoggedUser().getId()) {
            recipe.setName(updatedRecipe.getName());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setCategory(updatedRecipe.getCategory());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setDirections(updatedRecipe.getDirections());
            recipe.setDate(LocalDateTime.now().toString());
            recipeRepository.save(recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
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
