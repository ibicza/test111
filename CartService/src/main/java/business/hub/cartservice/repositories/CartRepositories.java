package business.hub.cartservice.repositories;

import business.hub.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepositories extends JpaRepository<Cart, Integer> {
    /*@Query("SELECT c.products FROM Cart c WHERE c.id = :cartId")
    List<Product> findProductsByCartId(@Param("cartId") Long cartId);*/

}
