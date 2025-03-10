package laboratory.fsqsWholeSale.application;

import laboratory.fsqsWholeSale.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
