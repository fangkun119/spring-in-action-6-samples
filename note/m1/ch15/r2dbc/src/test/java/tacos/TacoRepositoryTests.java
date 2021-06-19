package tacos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.annotation.DirtiesContext;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tacos.Ingredient.Type;

@DataR2dbcTest(includeFilters = @Filter(
    classes = R2dbcTestConfiguration.class, 
    type = FilterType.ASSIGNABLE_TYPE))
@DirtiesContext
public class TacoRepositoryTests {

  @Autowired
  IngredientRepository ingredientRepo;
  
  @Autowired
  TacoRepository tacoRepo;

  @BeforeEach
  public void setupIngredients() {
    Flux.just(
        new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
        new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
        new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE))
      .flatMap(ingredient -> {
        return ingredientRepo.save(ingredient);
      })
      .subscribe();
  }
  
  @Test
  public void shouldSaveAndLoadTacos() {
    Taco testTaco = new Taco("Test Taco");
    Flux.just("FLTO", "CHED")
        .flatMap(slug -> {
          return ingredientRepo.findBySlug(slug);
        })
        .map(ingredient -> {
            testTaco.addIngredient(ingredient);
            return ingredient;
        })
        .subscribe();
    
    StepVerifier
        .create(tacoRepo.save(testTaco))
        .assertNext(this::assertTaco)
        .verifyComplete();

    StepVerifier
        .create(tacoRepo.findById(1L))
        .assertNext(this::assertTaco)
        .verifyComplete();

    StepVerifier
        .create(tacoRepo.findAll())
        .assertNext(this::assertTaco)
        .verifyComplete();
  }

  private void assertTaco(Taco taco) {
    assertThat(taco.getId()).isEqualTo(1L);
    assertThat(taco.getName()).isEqualTo("Test Taco");
    assertThat(taco.getIngredientIds()).contains(1L, 3L);
  }

}
