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

    // Method to get paginated products based on page and size
    @Transactional
    public List<Product> getAllProducts(int page, int size) {
        // Fetch paginated products using PageRequest
        List<Product> products = productRepository.findAll(PageRequest.of(page, size)).getContent();
        if (products.isEmpty()) {
            System.out.println("No products found in the database.");
        }
        return products;
    }

    public Page<Product> getPaginatedProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return productRepository.findAll(pageRequest);
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
