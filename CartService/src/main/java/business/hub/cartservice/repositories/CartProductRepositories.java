package business.hub.cartservice.repositories;

import business.hub.cartservice.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepositories extends JpaRepository<CartProduct, Integer> {
}
