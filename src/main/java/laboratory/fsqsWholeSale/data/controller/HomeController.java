package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.service.CartService;
import laboratory.fsqsWholeSale.data.service.ProductService;
import laboratory.fsqsWholeSale.data.service.RssService;
import laboratory.fsqsWholeSale.data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CartService cartService;


    @Autowired
    private ProductService productService;

    @Autowired
    private RssService rssService;

    // Default home page and products page combined
    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "4") int pageSize, Model model) {
        return getProductsPage(page, pageSize, model);
    }

    // Handle /products page, pagination included
    @GetMapping("/products")
    public String productsPage(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "4") int pageSize, Model model) {
        return getProductsPage(page, pageSize, model);
    }

    private String getProductsPage(int page, int pageSize, Model model) {
        try {
            // Fetch paginated products from ProductService
            Page<Product> productPage = productService.getPaginatedProducts(page, pageSize);

            // Add attributes to the model
            model.addAttribute("products", productPage.getContent()); // Current page products
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages()); // Total pages for pagination control
            model.addAttribute("hasNextPage", productPage.hasNext());
            model.addAttribute("hasPreviousPage", productPage.hasPrevious()); // Add previous page support

            // Fetch and add the first RSS feed (Default RSS)
            // String rssUrl = "https://lepatriote.ci/rss/category/economie"; // Default RSS URL
            String rssUrl = "https://www.bankofcanada.ca/content_type/canadian-survey-of-consumer-expectations/feed/";
            model.addAttribute("rssFeeds", rssService.fetchRssFeed(rssUrl)); // Pass the fetched RSS feeds

            // Fetch and add the second RSS feed (You can change the URL below)
            String secondRssUrl = "https://www.cirad.fr/rss/actualites"; // Second RSS URL
            model.addAttribute("secondRssFeeds", rssService.fetchRssFeed(secondRssUrl)); // Pass second RSS feeds

            model.addAttribute("loading", false);
            model.addAttribute("error", null);
        } catch (Exception e) {
            // Handle errors
            model.addAttribute("error", "Failed to load products or RSS feeds.");
            model.addAttribute("loading", false);
            model.addAttribute("products", null);
        }

        return "index"; // Return the view (index.html for Thymeleaf)
    }


    @GetMapping("/inventaire-magasin")
    public String showInventoryPage(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        // Fetch paginated products based on the page and pageSize
        model.addAttribute("products", productService.getAllProducts(page, pageSize));
        return "inventaire-magasin"; // Thymeleaf template
    }


    // Display add product form
    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); // Provide an empty Product object for the form
        return "add-product";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            Model model) {
        try {
            // ✅ Correct: Fetch product from productService
            Product product = productService.getProductById(productId);

            if (product == null) {
                throw new IllegalArgumentException("Product not found");
            }

            // ✅ Correctly add the product to cart
            cartService.addToCart(product, quantity);

            // ✅ Fetch updated cart details
            List<CartItem> cartItems = cartService.getCartItems();
            BigDecimal totalPrice = cartService.calculateTotalPrice();
            BigDecimal shippingCost = cartService.calculateShippingCost();
            BigDecimal totalAmount = totalPrice.add(shippingCost);

            // ✅ Add attributes for Thymeleaf
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("shippingCost", shippingCost);
            model.addAttribute("totalAmount", totalAmount);

            return "cart";  // ✅ Return cart page

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error adding product to cart.");
            return "error";  // ✅ Ensure there's an error.html page
        }
    }


    // Handle the add product form submission
    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product, Model model) {
        try {
            productService.saveProduct(product);
            return "redirect:/";  // Redirect to home after adding
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product.");
            return "add-product"; // Stay on the page in case of an error
        }
    }

    // Search Products
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> searchResults = productService.searchProducts(query);
        model.addAttribute("products", searchResults);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1); // Only one page for search results
        return "index"; // Ensure "index.html" contains the product table
    }
}