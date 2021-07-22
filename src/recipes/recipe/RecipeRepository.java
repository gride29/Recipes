package recipes.recipe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
