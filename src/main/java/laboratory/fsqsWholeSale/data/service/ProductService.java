package laboratory.fsqsWholeSale.data.service;

import jakarta.transaction.Transactional;
import laboratory.fsqsWholeSale.data.CategoryRepository;
import laboratory.fsqsWholeSale.data.ProductRepository;
import laboratory.fsqsWholeSale.data.model.Product;
import laboratory.fsqsWholeSale.data.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public List<Product> getAllProducts(int page, int size) {
        List<Product> products = productRepository.findAll(PageRequest.of(page, size)).getContent();
        if (products.isEmpty()) {
            System.out.println("No products found in the database.");
        }
        return products;
    }

    public Page<Product> getPaginatedProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page - 1, size));
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Product createProduct(String name, String description, double price, int stock, String categoryName, String imageUri) {
        // Fetch category from DB instead of using Product.Category enum
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

        Product product = new Product(name, description, BigDecimal.valueOf(price), stock, categoryName, imageUri);
        return productRepository.save(product);
    }



    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Category not found: " + name));
    }
}
