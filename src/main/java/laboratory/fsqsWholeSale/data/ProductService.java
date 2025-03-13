package laboratory.fsqsWholeSale.data;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll(); // Ensure this is returning the correct list
        if (products.isEmpty()) {
            System.out.println("No products found in the database.");
        }
        return products;
    }

    public Page<Product> getPaginatedProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page - 1, size));
    }

    public Product getProductById(Long id) {
        // Fetch the product by ID, returning null if not found
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        // Save the product to the repository
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        // Delete the product by ID
        productRepository.deleteById(id);
    }
}
