package laboratory.fsqsWholeSale.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
class ProductController {

    @Autowired
    private ProductService productService;


    // Get paginated products with dynamic page and size parameters
    @GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        return productService.getPaginatedProducts(page, size);  // Using page and size for pagination
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
