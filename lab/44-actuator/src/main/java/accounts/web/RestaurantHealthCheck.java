package accounts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import rewards.internal.restaurant.RestaurantRepository;

@Component
public class RestaurantHealthCheck implements HealthIndicator {

	RestaurantRepository restaurantRepository;

	@Autowired
	public RestaurantHealthCheck(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	@Override
	public Health health() {
		Long restaurantCount = restaurantRepository.getRestaurantCount();
		if (restaurantCount == 0) {
			return Health.status("NO_RESTAURANTS").withDetail("count", restaurantCount).build();
		} else {
			return Health.up().build();
		}
	}
}

