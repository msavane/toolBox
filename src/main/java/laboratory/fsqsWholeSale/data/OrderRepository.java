package laboratory.fsqsWholeSale.data;

import laboratory.fsqsWholeSale.data.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findById(Long id);  // ✅ Corrected return type to Optional<Order>
}
