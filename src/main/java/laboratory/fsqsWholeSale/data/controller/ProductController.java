package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.service.ProductService;
import laboratory.fsqsWholeSale.data.service.RssService;
import laboratory.fsqsWholeSale.data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RssService rssService;

    // Get paginated products with dynamic page and size parameters
    @GetMapping
    public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size) {
        return productService.getPaginatedProducts(page, size);
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

    // Fetch latest RSS news from the first source
    @GetMapping("/news")
    public List<String> getNewsFeed() {
        return rssService.fetchRssFeed("https://lepatriote.ci/rss/category/economie");
    }

    // Fetch latest RSS news from a second source
    @GetMapping("/news/secondary")
    public List<String> getSecondaryNewsFeed() {
        return rssService.fetchRssFeed("https://another-source.com/rss-feed.xml");
    }





}
