package tacos.data;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@DataCassandraTest
public class IngredientRepositoryTest {

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
      .create(ingredientRepo.findAll())
      .recordWith(ArrayList::new)
      .thenConsumeWhile(x -> true)
      .consumeRecordedWith(matches -> {
        assertThat(matches).contains(
            new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
        assertThat(matches).contains(
            new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
        assertThat(matches).contains(
            new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE));
        })
        .verifyComplete();

    StepVerifier
      .create(ingredientRepo.findById("CHED"))
      .expectNext(
          new Ingredient("CHED", "Cheddar Cheese", Type.CHEESE))
      .verifyComplete();
  }
  
}
