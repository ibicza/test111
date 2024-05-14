package business.hub.cartservice.repositories;

import business.hub.cartservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepositories extends JpaRepository<Product, Integer> {
}
