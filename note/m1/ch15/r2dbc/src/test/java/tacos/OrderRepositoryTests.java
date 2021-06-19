package tacos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.annotation.DirtiesContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest(includeFilters = @Filter(
    classes = R2dbcTestConfiguration.class, 
    type = FilterType.ASSIGNABLE_TYPE))
@DirtiesContext
public class OrderRepositoryTests {

	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	TacoRepository tacoRepo;
	
  Taco taco1 = new Taco("Test Taco One");
  Taco taco2 = new Taco("Test Taco Two");

	@Test
	public void shouldSaveAndFetchTacoOrders() {
		TacoOrder newOrder = new TacoOrder();
		newOrder.setDeliveryName("Test Customer");
		newOrder.setDeliveryStreet("1234 North Street");
		newOrder.setDeliveryCity("Notrees");
		newOrder.setDeliveryState("TX");
		newOrder.setDeliveryZip("79759");
		newOrder.setCcNumber("4111111111111111");
		newOrder.setCcExpiration("12/24");
		newOrder.setCcCVV("123");
		
    Mono<TacoOrder> savedOrderMono = Mono.just(newOrder)
			.map(order -> {
				Flux.just(taco1, taco2)
					.flatMap(taco -> tacoRepo.save(taco))
					.doOnNext(taco -> order.addTaco(taco))
					.subscribe();
				return order;
			})
			.flatMap(order -> orderRepo.save(order));
    
		StepVerifier.create(savedOrderMono)
			.assertNext(this::assertTacoOrder)
			.verifyComplete();
		
		StepVerifier.create(orderRepo.count())
			.expectNext(1L)
			.verifyComplete();
		
		StepVerifier.create(orderRepo.findById(1L))
		  .assertNext(this::assertTacoOrder)
			.verifyComplete();

	}
	
	private void assertTacoOrder(TacoOrder tacoOrder) {
    assertThat(tacoOrder.getDeliveryName()).isEqualTo("Test Customer");
    assertThat(tacoOrder.getDeliveryStreet()).isEqualTo("1234 North Street");
    assertThat(tacoOrder.getDeliveryCity()).isEqualTo("Notrees");
    assertThat(tacoOrder.getDeliveryState()).isEqualTo("TX");
    assertThat(tacoOrder.getDeliveryZip()).isEqualTo("79759");
    assertThat(tacoOrder.getCcNumber()).isEqualTo("4111111111111111");
    assertThat(tacoOrder.getCcExpiration()).isEqualTo("12/24");
    assertThat(tacoOrder.getCcCVV()).isEqualTo("123");
    assertThat(tacoOrder.getTacoIds()).hasSize(2);
    assertThat(tacoOrder.getTacoIds()).contains(1L, 2L);
	}
	
}
