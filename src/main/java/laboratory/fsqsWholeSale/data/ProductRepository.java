package laboratory.fsqsWholeSale.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);  // Fetch product by ID
    Page<Product> findAll(Pageable pageable);  // Fetch paginated products
    List<Product> findByNameContainingIgnoreCase(String name);
}