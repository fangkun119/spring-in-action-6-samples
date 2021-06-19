package tacos;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tacos.Ingredient.Type;

@DataR2dbcTest(includeFilters = @Filter(
    classes = R2dbcTestConfiguration.class, 
    type = FilterType.ASSIGNABLE_TYPE))
@DirtiesContext
public class IngredientRepositoryTests {

  @Autowired
  IngredientRepository ingredientRepo;
  
  @Test
  public void shouldSaveAndFetchIngredients() {
    
    Flux.just(
          new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
          new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
          new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE))
        .flatMap(ingredientRepo::save)
        .subscribe();
    
    StepVerifier
        .create(ingredientRepo.findBySlug("FLTO"))
        .assertNext(ingredient -> {
          assertThat(ingredient.getSlug()).isEqualTo("FLTO");
          assertThat(ingredient.getName()).isEqualTo("Flour Tortilla");
          assertThat(ingredient.getType()).isEqualTo(Type.WRAP);
        })
        .verifyComplete(); 
    
    StepVerifier
        .create(ingredientRepo.findAll())
        .assertNext(ingredient -> {
          assertThat(ingredient.getSlug()).isEqualTo("FLTO");
          assertThat(ingredient.getName()).isEqualTo("Flour Tortilla");
          assertThat(ingredient.getType()).isEqualTo(Type.WRAP);
        })
        .assertNext(ingredient -> {
          assertThat(ingredient.getSlug()).isEqualTo("GRBF");
          assertThat(ingredient.getName()).isEqualTo("Ground Beef");
          assertThat(ingredient.getType()).isEqualTo(Type.PROTEIN);
        })
        .assertNext(ingredient -> {
          assertThat(ingredient.getSlug()).isEqualTo("CHED");
          assertThat(ingredient.getName()).isEqualTo("Cheddar Cheese");
          assertThat(ingredient.getType()).isEqualTo(Type.CHEESE);
        })
        .verifyComplete();
    
  }
  
}
