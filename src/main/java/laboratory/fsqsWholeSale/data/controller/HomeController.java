package laboratory.fsqsWholeSale.data.controller;

import laboratory.fsqsWholeSale.data.model.Product;
import laboratory.fsqsWholeSale.data.model.CartItem;
import laboratory.fsqsWholeSale.data.service.ProductService;
import laboratory.fsqsWholeSale.data.service.CartService;
import laboratory.fsqsWholeSale.data.service.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private RssService rssService;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "4") int pageSize,
                       Model model) {
        return getProductsPage(page, pageSize, model);
    }

    @GetMapping("/products")
    public String productsPage(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "4") int pageSize,
                               Model model) {
        return getProductsPage(page, pageSize, model);
    }

    private String getProductsPage(int page, int pageSize, Model model) {
        try {
            Page<Product> productPage = productService.getPaginatedProducts(page, pageSize);

            model.addAttribute("products", productPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("hasNextPage", productPage.hasNext());
            model.addAttribute("hasPreviousPage", productPage.hasPrevious());

            String rssUrl = "https://www.bankofcanada.ca/content_type/canadian-survey-of-consumer-expectations/feed/";
            model.addAttribute("rssFeeds", rssService.fetchRssFeed(rssUrl));

            String secondRssUrl = "https://www.cirad.fr/rss/actualites";
            model.addAttribute("secondRssFeeds", rssService.fetchRssFeed(secondRssUrl));

            model.addAttribute("loading", false);
            model.addAttribute("error", null);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load products or RSS feeds.");
            model.addAttribute("loading", false);
            model.addAttribute("products", null);
        }
        return "index";
    }

    @GetMapping("/inventaire-magasin")
    public String showInventoryPage(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "4") int pageSize,
                                    Model model) {
        model.addAttribute("products", productService.getAllProducts(page, pageSize));
        return "inventaire-magasin";
    }

    @GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product, Model model) {
        try {
            productService.saveProduct(product);
            return "index";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add product.");
            return "add-product";
        }
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> searchResults = productService.searchProducts(query);
        model.addAttribute("products", searchResults);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1); // Only one page for search results
        return "index"; // Ensure "index.html" contains the product table
    }


    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") int quantity,
                            Model model) {
        try {
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new IllegalArgumentException("Product not found");
            }

            cartService.addToCart(product, quantity);

            List<CartItem> cartItems = cartService.getCartItems();
            BigDecimal totalPrice = cartService.calculateTotalPrice();
            BigDecimal shippingCost = cartService.calculateShippingCost();
            BigDecimal totalAmount = totalPrice.add(shippingCost);

            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("shippingCost", shippingCost);
            model.addAttribute("totalAmount", totalAmount);

            return "cart";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error adding product to cart.");
            return "error";
        }
    }
}
