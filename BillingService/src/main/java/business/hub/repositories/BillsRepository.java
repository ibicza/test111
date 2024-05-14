package business.hub.repositories;

import business.hub.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BillsRepository extends JpaRepository<Bill, Long> {
}
